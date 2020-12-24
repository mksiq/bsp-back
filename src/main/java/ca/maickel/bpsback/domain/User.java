package ca.maickel.bpsback.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class User implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String userName;
    private String email;
    private String password;
    private LocalDate signUpDate;

    @OneToMany(mappedBy = "buyer")
    private List<Transaction> boughtTransactions = new ArrayList<>();
    @OneToMany(mappedBy = "seller")
    private List<Transaction> soldTransactions = new ArrayList<>();

    public User(){};

    public User(Integer id, String userName, String email, String password, LocalDate signUpDate) {
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
}
