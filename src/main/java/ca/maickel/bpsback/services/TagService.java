package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.dto.TagDTO;

import java.util.List;
import java.util.Set;

public interface TagService {

  public Tag find(Integer id);

  public List<Tag> findAll();

  public Tag findByTag(String tag);

  public Tag insert(Tag obj);

  public Tag fromDTO(TagDTO objDTO);

  public Set<Tag> insertNTags(Set<Tag> tags);

  public void delete(Integer id);

  public Tag update(Tag obj);
}
