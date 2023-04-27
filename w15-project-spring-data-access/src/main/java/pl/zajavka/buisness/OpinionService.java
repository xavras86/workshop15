package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Purchase;

import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class OpinionService {

    private final PurchaseService purchaseService;
    private final OpinionRepository opinionRepository;

    @Transactional
    public Opinion create(Opinion opinion) {
        //sprawdzenie czy klient faktycznie dokonal zakupu i moze dodac opinie
        String email = opinion.getCustomer().getEmail();
        String productCode = opinion.getProduct().getProductCode();
        List<Purchase> purchases = purchaseService.findAll(email, productCode);
        log.debug("Customer: [{}] made: [{}] purchases for product: [{}]", email, purchases.size(), productCode);
        if (purchases.isEmpty()) {
            throw new RuntimeException("Customer: [%s] wants to give opinion for product : [%s] but there is no purchase"
                    .formatted(email, productCode));
        }
        return opinionRepository.create(opinion);
    }

    @Transactional
    public void removeAll() {
        opinionRepository.removeAll();
    }

    @Transactional
    public void removeAll(String email) {
        opinionRepository.remove(email);

    }

    public List<Opinion> findAll(String email) {
        return opinionRepository.findAll(email);
    }

    public List<Opinion> findAll() {
        return opinionRepository.findAll();
    }

    @Transactional
    public List<Opinion> findUnwantedOpinions() {
        return opinionRepository.findUnwantedOpinions();
    }

    @Transactional
    public void removeUnwantedOpinions() {
        opinionRepository.removeUnwantedOpinions();
    }

    public boolean customerGivesUnwantedOpinions(String email) {
        return opinionRepository.customerGivesUnwantedOpinions(email);
    }

    public List<Opinion> findAllByProductCode(String productCode) {
        return opinionRepository.findAllByProductCode(productCode);
    }

    public void removeAllByProductCode(String productCode) {
        opinionRepository.removeAllByProductCode( productCode);
    }
}



