package ca.maickel.bpsback.repositories;

import ca.maickel.bpsback.domain.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface TagRepository extends JpaRepository<Tag, Integer> {
  @Transactional
  Tag findByTagIgnoreCase(String tag);
}
