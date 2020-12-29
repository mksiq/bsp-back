package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;

import java.util.Date;

public abstract class AbstractEmailService implements EmailService {

    @Value("${default.sender}")
    private String sender;

    @Override
    public void sendSignupConfirmationEmail(User obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromUser(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromUser(User obj){
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getEmail());
        sm.setFrom(sender);
        sm.setSubject("Welcome to Banana Photo Store");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        String message = obj.toString();
        message = "Welcome, " + message;
        sm.setText(message);
        return sm;
    }

    @Override
    public void sendTransactionConfirmationEmail(Transaction obj) {
        SimpleMailMessage sm = prepareSimpleMailMessageFromTransaction(obj);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareSimpleMailMessageFromTransaction(Transaction obj) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(obj.getBuyer().getEmail());
        sm.setFrom(sender);
        sm.setSubject("Your photo transaction confirmation");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText(obj.toString());
        return sm;
    }

    @Override
    public void sendNewPasswordEmail(User user, String password){
        SimpleMailMessage sm = prepareNewPasswordEmail(user, password);
        sendEmail(sm);
    }

    protected SimpleMailMessage prepareNewPasswordEmail(User user, String password) {
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setTo(user.getEmail());
        sm.setFrom(sender);
        sm.setSubject("BPS: New password requested");
        sm.setSentDate(new Date(System.currentTimeMillis()));
        sm.setText("New password: " + password);
        return sm;
    }
}
