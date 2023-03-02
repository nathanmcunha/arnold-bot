/* (C) 2023 */
package br.com.nathancunha.arnold.bot.repository.attendance;

import br.com.nathancunha.arnold.bot.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttendanceRepository
    extends JpaRepository<Attendance, Long>, CustomAttendanceRepository {}
