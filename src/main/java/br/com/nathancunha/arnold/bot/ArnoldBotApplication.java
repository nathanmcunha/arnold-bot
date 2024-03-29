package br.com.nathancunha.arnold.bot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EnableAutoConfiguration
@EnableJpaRepositories(
		basePackages = "br.com.nathancunha.arnold.bot.repository",
		repositoryImplementationPostfix = "Impl")
public class ArnoldBotApplication {

	public static void main(String[] args) {
		SpringApplication.run(ArnoldBotApplication.class, args);
	}

}
