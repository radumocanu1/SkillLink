package unibuc.SkillLink;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import unibuc.SkillLink.services.ProfilePictureService;

@SpringBootApplication(scanBasePackages = {"unibuc.SkillLink"})
@EnableJpaRepositories
public class SkillLinkApplication {

	public static void main(String[] args) {
		SpringApplication.run(SkillLinkApplication.class, args);
	}

	@Bean
	CommandLineRunner init(ProfilePictureService profilePictureService) {
		return args -> {
			profilePictureService.init();
		};
	}
}
