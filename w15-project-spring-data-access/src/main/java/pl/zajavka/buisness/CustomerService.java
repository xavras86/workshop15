package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class CustomerService {

    private OpinionService opinionService;
    private PurchaseService purchaseService;
    private final CustomerRepository customerRepository;

    @Transactional
    public void removeAll() {
        opinionService.removeAll();
        purchaseService.removeAll();
        customerRepository.removeAll();



    }
}


