package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.dto.TagDTO;
import ca.maickel.bpsback.repositories.TagRepository;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagServiceImpl implements TagService {

  private final TagRepository repo;

  public TagServiceImpl(TagRepository repo) {
    this.repo = repo;
  }

  @Override
  public Tag find(Integer id) {
    Optional<Tag> obj = repo.findById(id);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + Tag.class.getName()));
  }

  @Override
  public List<Tag> findAll() {
    return repo.findAll();
  }

  /** Looks for a tag ignoring the case */
  @Override
  public Tag findByTag(String tag) {
    return repo.findByTagIgnoreCase(tag);
  }

  /** Saves a new tag in lower case */
  @Override
  public Tag insert(Tag obj) {
    Tag foundTag = findByTag(obj.getTag());
    if (foundTag == null) {
      obj.setId(null);
      obj.setTag(obj.getTag().toLowerCase(Locale.ROOT));
      obj = repo.save(obj);
    } else {
      obj = foundTag;
    }
    return obj;
  }

  @Override
  public Tag fromDTO(TagDTO objDTO) {
    return new Tag(objDTO);
  }

  /** Inserts new tags while updating id for existing ones */
  @Override
  public Set<Tag> insertNTags(Set<Tag> tags) {
    tags =
        tags.stream()
            .map(
                tag -> {
                  Tag foundTag = findByTag(tag.getTag());
                  if (foundTag != null) {
                    tag = foundTag;
                  } else {
                    tag = insert(tag);
                  }
                  return tag;
                })
            .collect(Collectors.toSet());
    return tags;
  }

  @Override
  public void delete(Integer id) {
    repo.deleteById(id);
  }

  @Override
  public Tag update(Tag obj) {
    Tag newObj = find(obj.getId());
    updateData(newObj, obj);
    newObj.setTag(newObj.getTag().toLowerCase(Locale.ROOT));
    return repo.save(newObj);
  }

  /** Allows only update for tag name */
  private void updateData(Tag newObj, Tag obj) {
    newObj.setTag(obj.getTag());
  }
}
