package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@AllArgsConstructor
public class OpinionService {

    private final OpinionRepository opinionRepository;

    @Transactional
    public void removeAll() {
        opinionRepository.removeAll();

    }
}



