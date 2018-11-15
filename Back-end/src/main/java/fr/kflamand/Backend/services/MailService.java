package fr.kflamand.Backend.services;

import fr.kflamand.Backend.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class MailService {

    private final String API_NAME = "PostPlatform";
    private final String SEPARATOR = "------------------------------------------------\n";

    //TODO Ajout fin uri controller registration
    private final String API_ROOT_URI = "http://localhost:8080/auth/Enabled/";

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Autowired
    public JavaMailSender emailSender;

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void sendSimpleMessage(String to, String subject, String text) {

        System.out.println("Sending email...");

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(to);
        message.setFrom("kev.flamand.dev.test@gmail.com");
        message.setSubject(subject);
        message.setText(text);
        message.setFrom("PostPlatform");

        try {
            emailSender.send(message);
            System.out.println("Email Sent!");
        } catch (MailException e) {
            System.out.println(e.getMessage());
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public String messageRegistrationMail(User user) {

        String UriValidMail = API_ROOT_URI;



        String message = "Hi " + user.getFullName() + ",\n \n This email has been sent from: " + this.API_NAME + "\n \n You have received this email because this email address" +
                "was used during registration for our API. If you did not register at our forums, please disregard this email. You do not need to unsubscribe or take " +
                "any further action. \n \n" + this.SEPARATOR + " Activation Instructions \n" + this.SEPARATOR + "\n Thank you for registering. We require that you \"validate\"" +
                " your registration to ensure that the email address you entered was correct. This protects against unwanted spam and malicious abuse. To activate your account, " +
                "simply click on the following link: \n \n" + UriValidMail + user.getRegistrationToken().getToken() + "\n \n (Some email client users may need to copy and paste the link into your web browser).";


        return message;
    }

    public String subjectRegistrationMail(User user) {

        String subject = "Wellcome to PostPlatform, " + user.getFullName() + ".";

        return subject;
    }
}