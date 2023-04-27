package pl.zajavka.buisness;

import pl.zajavka.domain.Customer;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> find(String email);

    Customer create(Customer customer);

    void removeAll();


    void remove(String email);


    List<Customer> findAll();
}
