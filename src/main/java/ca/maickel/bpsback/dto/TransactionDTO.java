package ca.maickel.bpsback.dto;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

public class TransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private LocalDate date;
    private Double listPrice;

    public TransactionDTO() {
    }
    public TransactionDTO(Transaction transaction) {
        id = transaction.getId();
        date = transaction.getDate();
        listPrice = transaction.getListPrice();
    }

    public TransactionDTO(Integer id, LocalDate date, Double listPrice) {
        this.id = id;
        this.date = date;
        this.listPrice = listPrice;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Double getListPrice() {
        return listPrice;
    }

    public void setListPrice(Double listPrice) {
        this.listPrice = listPrice;
    }
}
