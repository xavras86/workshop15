package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

@Slf4j
@Service
@AllArgsConstructor
public class ReloadDataService {

    private CustomerService customerService;
    private ProducerService producerService;
    private RandomDataService randomDataService;

    private ReloadDataRepository reloadDataRepository;

    @Transactional
    public void loadRandomData() {
        customerService.removeAll();
        producerService.removeAll();
        for (int i = 0; i < 10; i++) {
            randomDataService.create();
        }
    }

    @Transactional
    public void reloadData() {
        customerService.removeAll();
        producerService.removeAll();

        try {
            Path filePath = ResourceUtils.getFile("classpath:w15-project-sql-inserts.sql").toPath();
            Stream.of(Files.readString(filePath).split("INSERT"))
                    .filter(line -> !line.isBlank())
                    .map(line -> "INSERT " + line)
                    .forEach(reloadDataRepository::run);
        } catch (Exception e) {
            log.error("Unable to load SQL inserts", e);
        }


    }

}
