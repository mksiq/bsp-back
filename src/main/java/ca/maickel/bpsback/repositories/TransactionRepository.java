package ca.maickel.bpsback.repositories;

import ca.maickel.bpsback.domain.Transaction;
import ca.maickel.bpsback.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

  @Transactional(readOnly = true)
  public List<Transaction> findAllByBuyerOrSeller(User buyer, User seller);

  @Transactional(readOnly = true)
  public List<Transaction> findAllByBuyer(User buyer);

  @Transactional(readOnly = true)
  public List<Transaction> findAllBySeller(User seller);
}
