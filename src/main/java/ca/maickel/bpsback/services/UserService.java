package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.UserDTO;
import ca.maickel.bpsback.repositories.TransactionRepository;
import ca.maickel.bpsback.repositories.UserRepository;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository repo;

    public UserService(UserRepository repo) {
        this.repo = repo;
    }

    public User find(Integer id){
        Optional<User> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + User.class.getName()));
    }

    public List<User> findAll() {
        return repo.findAll();
    }

    @Transactional
    public User insert(User obj){
        obj.setId(null);
        obj = repo.save(obj);
        return obj;
    }

    public User fromDTO(UserDTO objDTO){
        User user = new User(null, objDTO.getUserName(), objDTO.getEmail(), objDTO.getPassword(), objDTO.getSignUpDate());
        return user;
    }
}
