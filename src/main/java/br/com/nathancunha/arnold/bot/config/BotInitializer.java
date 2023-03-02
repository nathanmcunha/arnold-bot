/* (C) 2023 */
package br.com.nathancunha.arnold.bot.config;

import br.com.nathancunha.arnold.bot.service.ArnoldBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Component
@Slf4j
public class BotInitializer {

  ArnoldBot telegramBot;

  public BotInitializer(ArnoldBot telegramBot) {
    this.telegramBot = telegramBot;
  }

  @EventListener({ContextRefreshedEvent.class})
  public void init() throws TelegramApiException {
    TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
    try {
      telegramBotsApi.registerBot(telegramBot);
    } catch (TelegramApiException e) {
      log.error("Error occurred: " + e.getMessage());
    }
  }
}
