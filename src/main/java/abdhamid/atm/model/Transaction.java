package abdhamid.atm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "transactions")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Transaction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private int refId;
    private String transactionType;
    private String transactionCreator;
    private Double amount;

    @CreationTimestamp
    private LocalDateTime timestamp;

    public Transaction(String transactionType, String transactionCreator, Double amount) {
        this.transactionType = transactionType;
        this.transactionCreator = transactionCreator;
        this.amount = amount;
    }

    public Transaction(int refId, String transactionType, String transactionCreator, Double amount, LocalDateTime timestamp) {
        this.refId = refId;
        this.transactionType = transactionType;
        this.transactionCreator = transactionCreator;
        this.amount = amount;
        this.timestamp = timestamp;
    }

}
