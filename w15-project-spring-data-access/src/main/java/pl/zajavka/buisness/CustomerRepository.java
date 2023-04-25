package pl.zajavka.buisness;

import pl.zajavka.domain.Customer;

public interface CustomerRepository {
    Customer create(Customer customer);

    void removeAll();
}
