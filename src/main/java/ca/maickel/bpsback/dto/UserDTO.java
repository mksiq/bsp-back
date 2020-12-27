package ca.maickel.bpsback.dto;

import ca.maickel.bpsback.domain.User;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class UserDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;
  @NotEmpty(message = "Must be filled")
  private String userName;
  @NotEmpty(message = "Must be filled")
  private String email;
  @NotEmpty(message = "Must be filled")
  private String password;
  private LocalDate signUpDate;

  private List<TransactionDTO> soldList = new ArrayList<>();
  private List<TransactionDTO> boughtList = new ArrayList<>();

  public UserDTO() {}

  public UserDTO(User user) {
    id = user.getId();
    userName = user.getUserName();
    email = user.getEmail();
    password = user.getEmail();
    signUpDate = user.getSignUpDate();
  }

  public UserDTO(Integer id, String userName, String email, String password, LocalDate signUpDate) {
    this.id = id;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.signUpDate = signUpDate;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public LocalDate getSignUpDate() {
    return signUpDate;
  }

  public void setSignUpDate(LocalDate signUpDate) {
    this.signUpDate = signUpDate;
  }

  public List<TransactionDTO> getSoldList() {
    return soldList;
  }

  public void setSoldList(List<TransactionDTO> soldList) {
    this.soldList = soldList;
  }

  public List<TransactionDTO> getBoughtList() {
    return boughtList;
  }

  public void setBoughtList(List<TransactionDTO> boughtList) {
    this.boughtList = boughtList;
  }
}
