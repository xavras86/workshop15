package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Purchase;

import java.util.List;
import java.util.Map;

@Service
@AllArgsConstructor
public class PurchaseService {


    private final PurchaseRepository purchaseRepository;

    @Transactional
    public Purchase create(Purchase purchase) {
        return purchaseRepository.create(purchase);
    }

    @Transactional
    public void removeAll() {
        purchaseRepository.removeAll();

    }

    @Transactional
    public void removeAll(String email) {
        purchaseRepository.removeAll(email);

    }

    public List<Purchase> findAll(String email) {
        return purchaseRepository.findAll(email);



    }

    public List<Purchase> findAll(String email, String productCode) {
        return purchaseRepository.findAll(email, productCode);
    }
}