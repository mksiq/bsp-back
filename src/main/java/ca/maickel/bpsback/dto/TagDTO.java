package ca.maickel.bpsback.dto;

import ca.maickel.bpsback.domain.Tag;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TagDTO implements Serializable {
  private static final long serialVersionUID = 1L;

  private Integer id;
  @NotEmpty(message = "Tag must be named")
  @Size(min = 2, max = 20, message = "Name length invalid")
  private String tag;
  private Set<PhotoDTONoTags> photos = new HashSet<>();

  public TagDTO() {}

  public TagDTO(Integer id, String tag) {
    this.id = id;
    this.tag = tag;
  }

  public TagDTO(Tag tag) {
    this.id = tag.getId();
    this.tag = tag.getTag();
    this.photos =
        tag.getPhotos().stream()
            .map(photo -> new PhotoDTONoTags(photo))
            .collect(Collectors.toSet());
  }

  public Set<PhotoDTONoTags> getPhotos() {
    return photos;
  }

  public void setPhotos(Set<PhotoDTONoTags> photos) {
    this.photos = photos;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTag() {
    return tag;
  }

  public void setTag(String tag) {
    this.tag = tag;
  }

  @Override
  public String toString() {
    return "TagDTO{" + "id=" + id + ", tag='" + tag + '\'' + ", photos=" + photos + '}';
  }
}
