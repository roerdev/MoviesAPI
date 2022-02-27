package com.roerdev.moviesapi.email;

import com.roerdev.moviesapi.config.SendEmailConfig;
import com.roerdev.moviesapi.models.UserEntity;
import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Slf4j
@Component
public class WelcomeEmailNotification {

    public void sendEmail(UserEntity user, SendEmailConfig sendEmailConfig) throws IOException, RuntimeException {

        Email fromEmail = new Email(sendEmailConfig.getFrom());
        Email toEmail = new Email(user.getEmail());
        Content content = new Content("text/html", " ");
        Mail mail = new Mail(fromEmail, "Bienvenido a la mejor API de películas", toEmail, content);
        mail.setTemplateId("d-2f38c5d6bdb3495e8a22e9ea3db852eb");

        SendGrid sg = new SendGrid(sendEmailConfig.getApikey());

        Request request = new Request();
        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
        } catch (IOException ex) {
            log.error("{}", ex.getMessage());
            throw new RuntimeException("Error al enviar correo");
        }
    }
}
