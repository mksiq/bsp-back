package ca.maickel.bpsback.domain;

import ca.maickel.bpsback.dto.NewPhotoDTO;
import ca.maickel.bpsback.dto.PhotoDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "photos")
public class Photo implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  private String fileName;
  private Integer width;
  private Integer height;
  private Double price;
  private LocalDate date;
  private String title;
  private Integer downloads;

  /** Decided to not ignore as I want to everytime I fetch a photo to receive all tags associated */
  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.MERGE})
  @JoinTable(
      name = "PHOTO_TAG",
      joinColumns = @JoinColumn(name = "photo_id"),
      inverseJoinColumns = @JoinColumn(name = "tag_id"))
  private Set<Tag> tags = new HashSet<>();

  @JsonIgnore
  @OneToMany(mappedBy = "photo")
  private List<Transaction> transactions = new ArrayList<>();

  /** User who owns and published the photo This allows deleting one user and having a null FK */
  @ManyToOne(optional = true, fetch = FetchType.LAZY)
  @JoinColumn(name = "user", columnDefinition = "integer")
  private User user;

  public Photo() {}

  public Photo(
      Integer id,
      String fileName,
      Integer width,
      Integer height,
      Double price,
      LocalDate date,
      String title,
      Integer downloads,
      User user) {
    this.id = id;
    this.fileName = fileName;
    this.width = width;
    this.height = height;
    this.price = price;
    this.date = date;
    this.title = title;
    this.downloads = downloads;
    this.user = user;
  }

  public Photo(PhotoDTO objDTO) {
    this.id = objDTO.getId();
    this.fileName = objDTO.getFileName();
    this.width = objDTO.getWidth();
    this.height = objDTO.getHeight();
    this.price = objDTO.getPrice();
    this.title = objDTO.getTitle();
    this.downloads = objDTO.getDownloads();
    this.user = new User(objDTO.getUser());
  }

  public Photo(NewPhotoDTO objDTO) {
    this.id = objDTO.getId();
    this.fileName = objDTO.getFileName();
    this.width = objDTO.getWidth();
    this.height = objDTO.getHeight();
    this.price = objDTO.getPrice();
    this.title = objDTO.getTitle();
    this.date = objDTO.getDate();
    this.downloads = objDTO.getDownloads();
    this.user = new User(objDTO.getUser());
    System.out.println("**** USER ID  ****");
    System.out.println(user.getId());
    this.tags =
        objDTO.getTags().stream().map(tagDTO -> new Tag(tagDTO)).collect(Collectors.toSet());
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public Integer getWidth() {
    return width;
  }

  public void setWidth(Integer width) {
    this.width = width;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Double getPrice() {
    return price;
  }

  public void setPrice(Double price) {
    this.price = price;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Integer getDownloads() {
    return downloads;
  }

  public void setDownloads(Integer downloads) {
    this.downloads = downloads;
  }

  public List<Transaction> getTransactions() {
    return transactions;
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  public Set<Tag> getTags() {
    for (int i = 0; i < tags.size(); ++i) {
      //      tags.get(i).setPhotos(null);
    }
    return tags;
  }

  public void setTags(Set<Tag> tags) {
    this.tags = tags;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Photo)) return false;
    return id != null && id.equals(((Photo) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "Photo{"
        + "id="
        + id
        + ", fileName='"
        + fileName
        + '\''
        + ", width="
        + width
        + ", height="
        + height
        + ", price="
        + price
        + ", date="
        + date
        + ", title='"
        + title
        + '\''
        + ", downloads="
        + downloads
        + ", tags="
        + tags
        + ", transactions="
        + transactions
        + ", user="
        + user
        + '}';
  }
}
