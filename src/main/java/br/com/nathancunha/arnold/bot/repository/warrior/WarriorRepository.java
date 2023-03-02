/* (C) 2023 */
package br.com.nathancunha.arnold.bot.repository.warrior;

import br.com.nathancunha.arnold.bot.model.Warrior;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WarriorRepository extends JpaRepository<Warrior, Long> {}
