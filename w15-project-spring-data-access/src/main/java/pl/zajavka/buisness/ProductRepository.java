package pl.zajavka.buisness;

import pl.zajavka.domain.Product;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {
    Product create(Product product);

    void removeAll();

    List<Product> findAll();


    Optional<Product> find(String productCode);

    void remove(String productCode);
}
