package pl.zajavka.buisness;

import pl.zajavka.domain.Producer;

public interface ProducerRepository {
    Producer create(Producer producer);
    void removeAll();
}
