package ca.maickel.bpsback.repositories;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PhotoRepository extends JpaRepository<Photo, Integer> {
    @Transactional
    List<Photo> findAllByUser(User user);
}
