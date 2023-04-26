package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Customer;

import java.time.LocalDate;
import java.util.Optional;

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
                .orElseThrow(() -> new RuntimeException("Customer with email [%s] is missing".formatted(email)));
    }

    @Transactional
    public void remove(String email) {
        Customer existingCustomer = find(email);


        opinionService.removeAll(email);
        purchaseService.removeAll(email);

        if(isOlderThan40(existingCustomer)) {
            throw new RuntimeException(
                    "Could now remove cutomer because he/she is older than 40, email: [%s]".formatted(email));
        }
        customerRepository.remove(email);

    }

    private boolean isOlderThan40(Customer existingCustomer) {
        return LocalDate.now().getYear() - existingCustomer.getDateOfBirth().getYear() >40;
    }


}


