package ca.maickel.bpsback.domain;

import ca.maickel.bpsback.dto.NewTransactionDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.text.NumberFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Entity
@Table(name = "transaction")
public class Transaction implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private LocalDate date;
  private Double listPrice;

  @ManyToOne
  @JoinColumn(name = "buyer_id", columnDefinition = "integer")
  private User buyer;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "seller_id", columnDefinition = "integer")
  private User seller;

  @ManyToOne
  @JoinColumn(name = "photo_id")
  private Photo photo;

  public Transaction() {}

  public Transaction(
      Integer id, LocalDate date, Double listPrice, User buyer, User seller, Photo photo) {
    this.id = id;
    this.date = date;
    this.listPrice = listPrice;
    this.buyer = buyer;
    this.seller = seller;
    this.photo = photo;
  }

  public Transaction(Integer id, LocalDate date, Double listPrice, Photo photo) {
    this.id = id;
    this.date = date;
    this.listPrice = listPrice;
    this.photo = photo;
  }

  public Transaction(NewTransactionDTO objDTO) {
    this.id = objDTO.getId();
    this.date = LocalDate.now();
    this.listPrice = objDTO.getPhoto().getPrice();
    this.buyer = objDTO.getBuyer();
    this.seller = objDTO.getPhoto().getUser();
    this.photo = objDTO.getPhoto();
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

    Transaction that = (Transaction) o;

    return id.equals(that.id);
  }

  @Override
  public int hashCode() {
    return id.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Photo: ");
    builder.append(getPhoto().getTitle());
    builder.append(", Bought on ");
    DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    builder.append(dateFormat.format(getDate()));
    builder.append(", From ");
    builder.append(getSeller().getUserName());
    builder.append(", For ");
    NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("en", "CA"));
    builder.append(formatter.format(getListPrice()));
    return builder.toString();
  }
}
