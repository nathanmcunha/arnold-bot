/* (C) 2023 */
package br.com.nathancunha.arnold.bot.repository.attendance;

import java.time.OffsetDateTime;

public interface CustomAttendanceRepository {

  int customPaymentExist(Long warriorId, OffsetDateTime paymentDate);

  int save(Long userId);
}
