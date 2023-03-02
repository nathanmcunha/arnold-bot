/* (C) 2023 */
package br.com.nathancunha.arnold.bot.repository.attendance;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import java.time.OffsetDateTime;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomAttendanceRepositoryImpl implements CustomAttendanceRepository {
  @Autowired private EntityManager entityManager;

  @Override
  public int customPaymentExist(Long warriorId, OffsetDateTime paymentDate) {
    Query query =
        entityManager.createNativeQuery(
            "SELECT * FROM attendance where"
                + " warrior_id = :warrior_id AND CAST(payment_date as date) = CAST(:pay_date as date)");

    query.setParameter("warrior_id", warriorId);
    query.setParameter("pay_date", paymentDate.toString());

    return query.getResultList().size();
  }

  @Override
  public int save(Long warriorId) {
    Query query =
        entityManager.createNativeQuery(
            " INSERT INTO public.attendance "
                + "(payment_date, warrior_id)"
                + "VALUES(now(), :warrior_id)");
    query.setParameter("warrior_id", warriorId);
    return query.executeUpdate();
  }
}
