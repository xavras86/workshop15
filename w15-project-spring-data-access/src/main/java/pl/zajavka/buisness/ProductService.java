package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Product;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;


    @Transactional
    public void removeAll() {
        productRepository.removeAll();

    }

    @Transactional
    public Product create(Product product) {
        return productRepository.create(product);
    }
}