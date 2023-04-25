package pl.zajavka.buisness;

import pl.zajavka.domain.Customer;

import java.util.Optional;

public interface CustomerRepository {
    Optional<Customer> find(String email);

    Customer create(Customer customer);

    void removeAll();
}
