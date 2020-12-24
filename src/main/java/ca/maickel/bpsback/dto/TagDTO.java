package ca.maickel.bpsback.dto;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.Tag;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TagDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String tag;

    public TagDTO() {
    }

    public TagDTO(Integer id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public TagDTO(Tag tag) {
        this.id = tag.getId();
        this.tag = tag.getTag();
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
}
