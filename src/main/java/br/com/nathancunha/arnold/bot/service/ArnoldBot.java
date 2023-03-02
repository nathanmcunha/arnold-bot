/* (C) 2023 */
package br.com.nathancunha.arnold.bot.service;

import br.com.nathancunha.arnold.bot.config.BotConfig;
import br.com.nathancunha.arnold.bot.model.Attendance;
import br.com.nathancunha.arnold.bot.model.Warrior;
import br.com.nathancunha.arnold.bot.repository.attendance.AttendanceRepository;
import br.com.nathancunha.arnold.bot.repository.warrior.WarriorRepository;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
public class ArnoldBot extends TelegramLongPollingBot {
  @Autowired BotConfig config;

  @Autowired WarriorRepository warriorRepository;

  @Autowired AttendanceRepository attendaceRepository;

  @Override
  public void onUpdateReceived(Update update) {

    if (update.hasMessage() && update.getMessage().hasText()) {
      try {
        if (update.getMessage().getText().equalsIgnoreCase("pago")) {

          SendMessage message = buildMessage(update);
          User user = update.getMessage().getFrom();
          if (warriorRepository.findById(user.getId()).isEmpty()) {
            registerWarrior(user);
          }

          var today = OffsetDateTime.now();
          var paymentExists = attendaceRepository.customPaymentExist(user.getId(), today) >= 1;

          if (!paymentExists) {
            Attendance attendance = new Attendance();
            attendance.setWarrior(buildWarrior(user));
            attendance.setPaymentDate(today);
            attendaceRepository.save(user.getId());
          }

          var msgText = buildMessageText(update, paymentExists);
          message.setText(msgText);
          execute(message);
        }
      } catch (TelegramApiException e) {
        System.out.println(e.getMessage());
      }
    }
  }

  private static String buildMessageText(Update update, boolean paymentExist) {
    Locale locale = new Locale("pt", "BR");
    String firstName = update.getMessage().getFrom().getFirstName();
    if (!paymentExist) {
      return firstName
          + " pagou o treino hoje: "
          + LocalDateTime.now()
              .format(DateTimeFormatter.ofPattern("EEE, dd/MM/yyy h:mm a", locale));
    }

    return "Coé !!, " + firstName + " tu já marcou presença hoje";
  }

  private static SendMessage buildMessage(Update update) {
    SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
    message.setChatId(update.getMessage().getChatId().toString());
    return message;
  }

  void processAttendance(User user, SendMessage message) {

    if (warriorRepository.findById(user.getId()).isEmpty()) {
      registerWarrior(user);
    }

    var today = OffsetDateTime.now();
    var paymentExists = attendaceRepository.customPaymentExist(user.getId(), today) <= 1;

    if (paymentExists) {
      var msg = "Coé !!, " + user.getFirstName() + "tu já marcou presença hoje";
      message.setText(msg);
    } else {
      Attendance attendance = new Attendance();
      attendance.setWarrior(buildWarrior(user));
      attendance.setPaymentDate(today);
      attendaceRepository.save(user.getId());
    }
  }

  @Override
  public String getBotUsername() {
    return config.getBotName();
  }

  @Override
  public String getBotToken() {
    return config.getToken();
  }

  private void registerWarrior(User user) {
    warriorRepository.save(buildWarrior(user));
  }

  private Warrior buildWarrior(User user) {
    final Warrior warrior = new Warrior();
    warrior.setId(user.getId());
    warrior.setFirstName(user.getFirstName());
    return warrior;
  }
}
