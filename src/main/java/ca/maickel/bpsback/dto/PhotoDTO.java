package ca.maickel.bpsback.dto;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.User;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Set;
import java.util.stream.Collectors;

public class PhotoDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;
  private String fileName;
  private Integer width;
  private Integer height;
  private Double price;
  private LocalDate date;
  private String title;
  private Integer downloads;
  private UserDTO user;
  private Set<TagDTONoPhotos> tags;

  public PhotoDTO() {}

  public PhotoDTO(
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
    this.user = new UserDTO(user);
  }

  public PhotoDTO(Photo photo) {
    this.id = photo.getId();
    this.fileName = photo.getFileName();
    this.width = photo.getWidth();
    this.height = photo.getHeight();
    this.price = photo.getPrice();
    this.date = photo.getDate();
    this.title = photo.getTitle();
    this.downloads = photo.getDownloads();
    try {
      this.user = new UserDTO(photo.getUser());
      this.user.setEmail(null);
      this.user.setPassword(null);
    } catch (NullPointerException e) {
      this.user = new UserDTO();
      this.user.setUserName("Removed User");
    }
    this.tags =
        photo.getTags().stream().map(tag -> new TagDTONoPhotos(tag)).collect(Collectors.toSet());
  }

  public Set<TagDTONoPhotos> getTags() {
    return tags;
  }

  public void setTags(Set<TagDTONoPhotos> tags) {
    this.tags = tags;
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

  public UserDTO getUser() {
    return user;
  }

  public void setUser(UserDTO user) {
    this.user = user;
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
}
