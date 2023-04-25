package pl.zajavka.buisness;

import pl.zajavka.domain.Product;

public interface ProductRepository {
    Product create(Product product);
    void removeAll();
}
