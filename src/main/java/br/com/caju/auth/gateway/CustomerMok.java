package br.com.caju.auth.gateway;

import java.util.ArrayList;
import java.util.List;

public class CustomerMok {

    private List<Customer> customers = new ArrayList<>();

    public CustomerMok() {
        customers.add(new Customer("UBER TRIP                   SAO PAULO BR","CASH"));
        customers.add(new Customer("UBER EATS                   SAO PAULO BR","MEAL"));
        customers.add(new Customer("PAG*JoseDaSilva          RIO DE JANEI BR","FOOD"));
        customers.add(new Customer("PICPAY*BILHETEUNICO           GOIANIA BR","CASH"));
        customers.add(new Customer("PADARIA DO ZE               SAO PAULO BR","MEAL"));
    }

    public Customer findByName(String name) {
        for (Customer customer : customers) {
            if (customer.getMerchant().equalsIgnoreCase(name)) {
                return customer;
            }
        }
        return null;
    }
}
