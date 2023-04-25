package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    @Transactional
    public void removeAll() {
        productRepository.removeAll();

    }
}