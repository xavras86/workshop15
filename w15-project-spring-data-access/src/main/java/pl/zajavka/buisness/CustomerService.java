package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Customer;

@Service
@AllArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;
    private OpinionService opinionService;
    private PurchaseService purchaseService;

    @Transactional
    public Customer create(Customer customer) {
        return customerRepository.create(customer);
    }

    @Transactional
    public void removeAll() {
        opinionService.removeAll();
        purchaseService.removeAll();
        customerRepository.removeAll();
    }

    public Customer find(String email) {
    return customerRepository.find(email)
            .orElseThrow(() -> new RuntimeException("Customer with emaile [%s] is missing".formatted(email) ));
    }
}


