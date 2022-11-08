package arnold;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/**
 * Bot
 */
public class ArnoldBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            try {
                if (update.getMessage().getText().equalsIgnoreCase("pago")
                        || update.getMessage().getText().equalsIgnoreCase("pago")) {

                    SendMessage message = new SendMessage(); // Create a SendMessage object with mandatory fields
                    message.setChatId(update.getMessage().getChatId().toString());
                    Locale locale = new Locale("pt", "BR");
                    var msg = update.getMessage().getFrom().getFirstName() + " pagou o treino hoje: "
                            + LocalDateTime.now().format(DateTimeFormatter.ofPattern("EEE, dd/MM/YYY h:mm a",locale));
                    message.setText(msg);
                    execute(message);
                }
            } catch (TelegramApiException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "";
    }

    @Override
    public String getBotToken() {
        return "";
    }

}
