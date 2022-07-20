package abdhamid.atm.dao;

import abdhamid.atm.model.Transaction;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class TransactionDao {
    public List<Transaction> readTransactionCSV(String path) {
        List<Transaction> transactions = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            Stream<String> lines = br.lines().skip(1);
            lines.forEachOrdered(line -> {
                String[] data = line.split(",");
                Transaction transaction = new Transaction();
                transaction.setRefId(Integer.parseInt(data[0]));
                transaction.setTransactionType(data[1]);
                transaction.setTransactionCreator(data[2]);
                transaction.setAmount(Double.valueOf(data[3]));
                transaction.setTimestamp(LocalDateTime.parse(data[4]));
                transactions.add(transaction);
            });
            br.close();
        } catch (FileNotFoundException fileNotFoundException) {
            File transactionFile = new File(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return transactions;
    }

    public void writeTransactionCSV(List<Transaction> transactions, String path) {
        try {
            List<String> toWrite = transactions.stream()
                    .map(transaction -> {
                        return new String[]{
                                String.valueOf(transaction.getRefId()),
                                transaction.getTransactionType(),
                                transaction.getTransactionCreator(),
                                String.valueOf(transaction.getAmount()),
                                String.valueOf(transaction.getTimestamp())
                        };
                    })
                    .map(data -> String.join(",", data)).toList();
            String[] header = {"referenceNumber", "type", "accountNumber", "amount", "timestamp"};
            BufferedWriter writer = new BufferedWriter(new FileWriter(path));
            writer.write(String.join(",", header));
            writer.newLine();
            for (String s : toWrite) {
                writer.write(s);
                writer.newLine();
            }
            writer.close();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}

