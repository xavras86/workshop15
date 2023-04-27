package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.buisness.OpinionService;
import pl.zajavka.buisness.ProductService;
import pl.zajavka.buisness.PurchaseService;
import pl.zajavka.buisness.ReloadDataService;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Purchase;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceTest {

    private ReloadDataService reloadDataService;

    private PurchaseService purchaseService;
    private OpinionService opinionService;
    private ProductService productService;

    @BeforeEach
    public void setUp() {
        assertNotNull(reloadDataService);
        assertNotNull(purchaseService);
        assertNotNull(opinionService);
        assertNotNull(productService);
        reloadDataService.reloadData();
    }

    @Test
    @DisplayName("Polecenie 10")
    void thatProductIsWiped() {
        //given
        final var productCode = "68084-618";

        productService.find(productCode);
        List<Opinion> opinionsBefore = opinionService.findAllByProductCode(productCode);
        List<Purchase> purchasesBefore = purchaseService.findAllByProductCode(productCode);

        Assertions.assertEquals(3, opinionsBefore.size());
        Assertions.assertEquals(4, purchasesBefore.size());

        //when

        productService.removeCompletely(productCode);

        //then

        RuntimeException exception = Assertions.assertThrows(RuntimeException.class, () -> productService.find(productCode));
        Assertions.assertEquals("Product with code [%s] is missing".formatted(productCode), exception.getMessage());

        Assertions.assertTrue(opinionService.findAllByProductCode(productCode).isEmpty());
        Assertions.assertTrue(purchaseService.findAllByProductCode(productCode).isEmpty());
    }
}
