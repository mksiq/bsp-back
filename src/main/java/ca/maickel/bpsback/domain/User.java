package ca.maickel.bpsback.domain;

import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.enums.Profile;
import ca.maickel.bpsback.security.UserSecurity;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "user")
public class User implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(unique = true)
  private String userName;

  @Column(unique = true)
  private String email;

  @ElementCollection(fetch = FetchType.EAGER)
  @CollectionTable(name = "profiles")
  private Set<Integer> profiles = new HashSet<>();

  @JsonIgnore private String password;
  private LocalDate signUpDate;

  @OneToMany(mappedBy = "buyer")
  private List<Transaction> boughtTransactions = new ArrayList<>();

  @OneToMany(mappedBy = "seller")
  private List<Transaction> soldTransactions = new ArrayList<>();

  @OneToMany
  @JoinColumn(name = "user")
  private List<Photo> photos = new ArrayList<>();

  public User(UserSecurity user) {
    this.id = user.getId();
    this.userName = user.getEmail();
    this.email = user.getUsername();
  }

  @PreRemove
  public void onPreRemove() {
    soldTransactions.stream()
        .forEach(
            transaction -> {
              transaction.setBuyer(null);
              transaction.setSeller(null);
            });
  }

  public Set<Profile> getProfiles() {
    return profiles.stream().map(profile -> Profile.toEnum(profile)).collect(Collectors.toSet());
  }

  public void addProfile(Profile profile) {
    profiles.add(profile.getCode());
  }

  public User() {
    addProfile(Profile.REGULAR);
  }
  ;

  public User(Integer id, String userName, String email, String password, LocalDate signUpDate) {
    addProfile(Profile.REGULAR);
    this.id = id;
    this.userName = userName;
    this.email = email;
    this.password = password;
    this.signUpDate = signUpDate;
  }

  public User(UserDTO user) {
    this.id = user.getId();
    this.userName = user.getUserName();
    this.email = user.getEmail();
    this.password = user.getPassword();
    this.signUpDate = user.getSignUpDate();
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

  public List<Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(List<Photo> photos) {
    this.photos = photos;
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

  public List<Transaction> getBoughtTransactions() {
    return boughtTransactions;
  }

  public void setBoughtTransactions(List<Transaction> boughtTransactions) {
    this.boughtTransactions = boughtTransactions;
  }

  public List<Transaction> getSoldTransactions() {
    return soldTransactions;
  }

  public void setSoldTransactions(List<Transaction> sellerTransactions) {
    this.soldTransactions = sellerTransactions;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    User user = (User) o;

    return id.equals(user.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append(getUserName());
    return builder.toString();
  }
}
