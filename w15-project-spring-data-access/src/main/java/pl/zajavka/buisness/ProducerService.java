package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Producer;

import java.util.List;

@Service
@AllArgsConstructor
public class ProducerService {
    private final ProductService productService;
    private final ProducerRepository producerRepository;


    @Transactional
    public void removeAll() {
        productService.removeAll();
        producerRepository.removeAll();

    }

    public List<Producer> findAll() {
        return producerRepository.findAll();
    }

    @Transactional
    public Producer create(Producer someProducer) {
        return producerRepository.create(someProducer);
    }
}

