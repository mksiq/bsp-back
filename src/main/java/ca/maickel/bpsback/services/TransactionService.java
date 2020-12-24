package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.repositories.TransactionRepository;
import ca.maickel.bpsback.services.exceptions.ObjectNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TransactionService {

    private final TransactionRepository repo;

    public TransactionService(TransactionRepository repo) {
        this.repo = repo;
    }

    public Transaction find(Integer id){
        Optional<Transaction> obj = repo.findById(id);
        return obj.orElseThrow(() -> new ObjectNotFoundException(
                "Object not Found: " + id + ", Type: " + Transaction.class.getName()));
    }

    public List<Transaction> findAll() {
        return repo.findAll();
    }

    public List<Transaction> findAllByBuyer(User buyer){
        return repo.findAllByBuyer(buyer);
    }

    public List<Transaction> findAllBySeller(User seller){
        return repo.findAllBySeller(seller);
    }
}
