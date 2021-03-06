package ca.maickel.bpsback.domain;

import ca.maickel.bpsback.dto.TagDTO;
import org.hibernate.annotations.NaturalId;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
/**
 * Tags defines tag categories for photos, each photo can have many tags And each tag can have many
 * photos. Tags are unique in its name.
 */
@Entity
@Table(name = "tag")
public class Tag implements Serializable {
  private static final long serialVersionUID = 1L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @NotEmpty
  @Size(max = 100)
  @NaturalId(mutable = true)
  private String tag;

  @ManyToMany(
      fetch = FetchType.LAZY,
      cascade = {CascadeType.PERSIST, CascadeType.MERGE},
      mappedBy = "tags")
  private Set<Photo> photos = new HashSet<>();

  public Tag() {}

  public Tag(Integer id, String tag) {
    this.id = id;
    this.tag = tag;
  }

  public Tag(TagDTO objDTO) {
    this.id = objDTO.getId();
    this.tag = objDTO.getTag();
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

  public Set<Photo> getPhotos() {
    return photos;
  }

  public void setPhotos(Set<Photo> photos) {
    this.photos = photos;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Tag)) return false;
    return id != null && id.equals(((Tag) o).getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

  @Override
  public String toString() {
    return "Tag{" + "id=" + id + ", tag='" + tag + '\'' + '}';
  }
}
