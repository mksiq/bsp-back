package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.dto.TagDTO;
import ca.maickel.bpsback.repositories.TagRepository;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.SQLDataException;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class TagService {

  private final TagRepository repo;

  public TagService(TagRepository repo) {
    this.repo = repo;
  }

  public Tag find(Integer id) {
    Optional<Tag> obj = repo.findById(id);
    return obj.orElseThrow(
        () ->
            new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + Tag.class.getName()));
  }

  public List<Tag> findAll() {
    return repo.findAll();
  }

  public Tag findByTag(String tag) {
    return repo.findByTagIgnoreCase(tag);
  }

  public Tag insert(Tag obj) {
    Tag foundTag = findByTag(obj.getTag());
    if (foundTag == null) {
      obj.setId(null);
      obj = repo.save(obj);
    } else {
      obj = foundTag;
    }
    return obj;
  }

  public Tag fromDTO(TagDTO objDTO) {
    return new Tag(objDTO);
  }

  public Set<Tag> insertNTags(Set<Tag> tags) {
    System.out.println("Before inserting");
    tags.forEach(tag -> System.out.println(tag));

    tags = tags.stream()
        .map(
            tag -> {
              Tag foundTag = findByTag(tag.getTag());
              if(foundTag != null){
                System.out.println("**ALREADY EXISTS**");
                System.out.println("**ALREADY EXISTS**");
                System.out.println(foundTag);
                tag = foundTag;
                System.out.println("**ASSIGNED**");
              } else {
                System.out.println("**DO NOT EXISTS**");
                System.out.println("**DO NOT EXISTS**");
                tag = insert(tag);
              }
              System.out.println(tag);

//              try {
//                System.out.println("**INSIDE TRY**");
//
//                tag = insert(tag);
//              } catch (Exception e) {
//                System.out.println("**INSIDE CATCH**");
//
//                System.out.println(e.getClass());
//                tag = findByTag(tag.getTag());
//              }
              return tag;
            }).collect(Collectors.toSet());


    System.out.println("After inserting");

    tags.forEach(tag -> System.out.println(tag));
    return tags;
  }
}
