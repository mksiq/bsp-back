package ca.maickel.bpsback.services;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import ca.maickel.bpsback.dto.NewTransactionDTO;

import java.util.List;

public interface TransactionService {

  public Transaction find(Integer id);

  public List<Transaction> findAll();

  public List<Transaction> findAllByBuyer(User buyer);

  public List<Transaction> findAllBySeller(User seller);

  public Transaction fromDTO(NewTransactionDTO objDTO);

  public Transaction insert(Transaction obj);

  public void delete(Integer id);
}
