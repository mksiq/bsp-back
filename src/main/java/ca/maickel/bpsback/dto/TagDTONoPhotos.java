package ca.maickel.bpsback.dto;

import ca.maickel.bpsback.domain.Tag;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class TagDTONoPhotos implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String tag;

    public TagDTONoPhotos() {
    }

    public TagDTONoPhotos(Integer id, String tag) {
        this.id = id;
        this.tag = tag;
    }

    public TagDTONoPhotos(Tag tag) {
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

    @Override
    public String toString() {
        return "TagDTONoPhotos{" +
                "id=" + id +
                ", tag='" + tag + '\'' +
                '}';
    }
}
