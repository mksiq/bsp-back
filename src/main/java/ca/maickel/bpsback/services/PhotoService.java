package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Photo;
import ca.maickel.bpsback.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhotoService {

    private final PhotoRepository repo;

    public PhotoService(PhotoRepository repo) {
        this.repo = repo;
    }

    public Photo find(Integer id){
        Optional<Photo> obj = repo.findById(id);
        return obj.orElse(null);
    }
}
