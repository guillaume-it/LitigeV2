package com.ruscassie.litige.config;

import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.ruscassie.litige.entity.Litige;
import com.ruscassie.litige.repository.LitigeRepository;

import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class LoadDatabase {

	@Value("${data.users:admin,userman,user1,user2,user3,user4,user5}")
	private String[] users;
	@Value("${data.nouns:sky,winter,sun,moon,spring,weekend,fall,summer,mountain,wolf,bird}")
	private String[] nouns;
	@Value("${data.verbs:falling,rising,exploding,shining,coming,lightning,howling,chirping}")
	private String[] verbs;
	@Value("${data.feelings:bad,numb,fine,good}")
	private String[] feelings;
	@Value("${data.timeOfDay:morning,afternoon,evening,night}")
	private String[] timeOfDay;

//	@Autowired
//	private PasswordEncoder passwordEncoder;

	@Value("clientSecret:secret")
	private String clientSecret;

	@Bean
	CommandLineRunner initLitige(final LitigeRepository repo) {
		return args -> {
			for (int i = 0; i < 100; i++) {

				log.info("save {}", repo.save(
						new Litige(null, "Objet " + i, "Localite " + i, new Date(), "Requerant " + i, "Agent " + i)));
			}
		};
	}

//	@Bean
//	CommandLineRunner initUsers(final UserRepository repo) {
//
//		return args -> {
//			for (int i = 0; i < users.length; i++) {
//				final String email = users[i] + "@" + users[i] + ".com";
//				final Role role = i > 1 ? Role.USER : i == 0 ? Role.ADMIN : Role.USER_MANAGER;
//				final String pwd = passwordEncoder.encode("pwd");
//				log.info("save {}", repo.save(new User(null, email, pwd, role)));
//			}
//		};
//	}

	int rnd(final int size) {
		return (int) (Math.random() * size);
	}

}
