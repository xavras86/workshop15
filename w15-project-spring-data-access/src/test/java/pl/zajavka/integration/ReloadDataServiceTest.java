package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.buisness.*;
import pl.zajavka.domain.*;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import java.util.List;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ReloadDataServiceTest {
    private ReloadDataService reloadDataService;
    private CustomerService customerService;
    private OpinionService opinionService;
    private ProducerService producerService;
    private ProductService productService;
    private PurchaseService purchaseService;


    @Test
    void thatDataIsReloaded() {
        //given, when

        reloadDataService.reloadData();

        //then

        List<Customer> allCustomers = customerService.findAll();
        List<Opinion> allOpinions = opinionService.findAll();
        List<Producer> allProducers = producerService.findAll();
        List<Product> allProducts = productService.findAll();
        List<Purchase> allPurchases = purchaseService.findAll();

        Assertions.assertEquals(100, allCustomers.size());
        Assertions.assertEquals(140, allOpinions.size());
        Assertions.assertEquals(20, allProducers.size());
        Assertions.assertEquals(50, allProducts.size());
        Assertions.assertEquals(300, allPurchases.size());

    }
}
