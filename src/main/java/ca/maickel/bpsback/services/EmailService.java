package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import org.springframework.mail.SimpleMailMessage;

public interface EmailService {

  void sendSignupConfirmationEmail(User obj);

  void sendTransactionConfirmationEmail(Transaction obj);

  void sendEmail(SimpleMailMessage msg);

  void sendNewPasswordEmail(User user, String password);
}
