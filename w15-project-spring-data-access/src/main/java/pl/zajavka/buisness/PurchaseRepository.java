package pl.zajavka.buisness;

import pl.zajavka.domain.Purchase;

public interface PurchaseRepository {
    Purchase create(Purchase purchase);
    void removeAll();
}
