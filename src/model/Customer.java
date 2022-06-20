package model;

public class Customer {
    private String name;
    private String pin;
    private Double balance;
    private String accountNumber;

    public Customer(String name, String pin, Double balance, String accountNumber) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.accountNumber = accountNumber;
    }

    public Customer() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public String toString() {
        return "model.Customer{" +
                "name=" + name  +
                ", pin=" + pin  +
                ", balance=" + balance +
                ", accountNumber=" + accountNumber +
                "}\n";
    }
}
