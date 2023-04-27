package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.buisness.OpinionService;
import pl.zajavka.buisness.ReloadDataService;
import pl.zajavka.domain.Opinion;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OpinionServiceTest {

    private ReloadDataService reloadDataService;
    private OpinionService opinionService;

    @BeforeEach
    void setUp() {
        assertNotNull(reloadDataService);
        assertNotNull(opinionService);
        reloadDataService.reloadData();
    }

    @Test
    @DisplayName("Polecenia 7")
    void thatUnwantedOpinionsAreRemoved() {
        //given
        assertEquals(140, opinionService.findAll().size());
        List<Opinion> unwantedOpinions = opinionService.findUnwantedOpinions();

        //when
        opinionService.removeUnwantedOpinions();

        //then

        assertEquals(140 - unwantedOpinions.size(), opinionService.findAll().size());

    }

}
