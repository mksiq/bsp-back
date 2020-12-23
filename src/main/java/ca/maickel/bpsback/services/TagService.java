package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Tag;
import ca.maickel.bpsback.repositories.TagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TagService {

    private final TagRepository repo;

    public TagService(TagRepository repo) {
        this.repo = repo;
    }

    public Tag find(Integer id){
        Optional<Tag> obj = repo.findById(id);
        return obj.orElse(null);
    }
}