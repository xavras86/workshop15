package pl.zajavka.buisness;

import pl.zajavka.domain.Purchase;

import java.util.List;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);

    void removeAll();

    void removeAll(String email);

    List<Purchase> findAll(String email);

    List<Purchase> findAll(String email, String productCode);

    List<Purchase> findAll();


    List<Purchase> findAllByProductCode(String productCode);

    void removeAllByProductCode(String productCode);
}
