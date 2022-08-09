package abdhamid.atm.repository;

import abdhamid.atm.model.Transaction;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Query("select t from transactions t where t.transactionCreator = ?1 order by t.timestamp desc")
    Page<Transaction> findByAccNumberDesc(String accNumber, Pageable pageable);
}
