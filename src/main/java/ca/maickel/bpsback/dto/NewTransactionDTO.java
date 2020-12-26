package ca.maickel.bpsback.dto;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.User;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

public class NewTransactionDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private User buyer;
    private User seller;
    private Photo photo;

    public NewTransactionDTO() {
    }

    public NewTransactionDTO(Integer id, User buyer, User seller, Photo photo) {
        this.id = id;
        this.buyer = buyer;
        this.seller = seller;
        this.photo = photo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public Photo getPhoto() {
        return photo;
    }

    public void setPhoto(Photo photo) {
        this.photo = photo;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        NewTransactionDTO that = (NewTransactionDTO) o;

        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
