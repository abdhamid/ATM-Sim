package abdhamid.atm.model;

import java.time.LocalDateTime;
import java.util.Random;

public class Transaction {
    private int refId;
    private String transactionType;
    private String transactionCreator;
    private Double amount;
    private LocalDateTime timestamp;

    public Transaction(int id, String transactionType, String transactionCreator, Double amount, LocalDateTime timestamp) {
        this.refId = id;
        this.transactionType = transactionType;
        this.transactionCreator = transactionCreator;
        this.amount = amount;
        this.timestamp = timestamp;
    }

    public Transaction() {

    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + refId +
                ", transactionType='" + transactionType + '\'' +
                ", transactionCreator='" + transactionCreator + '\'' +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                '}';
    }

    public int getRefId() {
        return refId;
    }

    public void setRefId(int refId) {
        this.refId = refId;
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public String getTransactionCreator() {
        return transactionCreator;
    }

    public void setTransactionCreator(String transactionCreator) {
        this.transactionCreator = transactionCreator;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public static int generateRefId() {
        Random rnd = new Random();
        return rnd.nextInt(999999);
    }
}
