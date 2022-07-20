package abdhamid.atm.dao;

import abdhamid.atm.model.Customer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


public class CustomerDao {
    public List<Customer> readCustomerCSV(String path) {
        List<Customer> customers = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));

            Stream<String> lines = br.lines().skip(1);
            lines.forEachOrdered(line -> {
                String[] data = line.split(",");
                Customer cust = new Customer();
                cust.setName(data[0]);
                cust.setPin(data[1]);
                cust.setBalance(Double.valueOf(data[2]));
                cust.setAccountNumber(data[3]);
                customers.add(cust);
            });
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return customers;
    }

    public static void writeCSV(List<Customer> customers, String path) {
        try {
            List<String> toWrite = customers.stream()
                    .map(customer -> {
                        return new String[]{
                                customer.getName(),
                                String.valueOf(customer.getPin()),
                                String.valueOf(customer.getBalance()),
                                String.valueOf(customer.getAccountNumber())
                        };
                    })
                    .map(data -> String.join(",", data)).toList();
            String[] header = {"name", "pin", "balance", "accountNumber"};
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(path))) {
                writer.write(String.join(",", header));
                writer.newLine();
                for (String s : toWrite) {
                    writer.write(s);
                    writer.newLine();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


}
