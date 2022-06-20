package dao;

import model.Customer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class CustomerDao {
    public static List<Customer> readCustomerCSV(String path) {
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

    public static void writeCSV() {

    }


}
