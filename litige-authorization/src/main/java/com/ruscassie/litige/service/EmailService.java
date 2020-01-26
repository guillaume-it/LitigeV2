package com.ruscassie.litige.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.ruscassie.litige.entity.User;

@Service
public class EmailService {
// https://o7planning.org/fr/11145/tutoriel-spring-email
	// http://dolszewski.com/spring/sending-html-mail-with-spring-boot-and-thymeleaf/
	@Autowired
	private JavaMailSender javaMailSender;

	@Autowired
	private TemplateEngine templateEngine;

	private String build(final String link) {
		final Context context = new Context();
		context.setVariable("activationLink", link);
		return templateEngine.process("emailWelcome", context);
	}

	public void prepareAndSend(final User user) {
		final MimeMessagePreparator messagePreparator = mimeMessage -> {
			final MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage);
			messageHelper.setFrom("arsel.cm@gmail.com");
			messageHelper.setTo("guillaume.ruscassie@outlook.fr");
			messageHelper.setSubject("Activation de votre compte Arsel");

			final String link = "http://localhost:4200/user/link-validation-email/" + user.getEmail() + "/"
					+ user.getTokenActiveAccount();
			final String content = build(link);
			messageHelper.setText(content, true);
		};
		try {
			javaMailSender.send(messagePreparator);
		} catch (final MailException e) {
			// TODO manage error
			// runtime exception; compiler will not force you to handle it
		}
	}

}