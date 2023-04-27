package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.buisness.*;
import pl.zajavka.domain.Customer;
import pl.zajavka.domain.Producer;
import pl.zajavka.domain.Product;
import pl.zajavka.domain.StoreFixtures;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ShoppingCartServiceTest {

    private ReloadDataService reloadDataService;
    private ShoppingCartService shoppingCartService;
    private CustomerService customerService;
    private PurchaseService purchaseService;
    private OpinionService opinionService;
    private ProductService productService;
    private ProducerService producerService;

    @BeforeEach
    public void setUp() {
        assertNotNull(reloadDataService);
        assertNotNull(shoppingCartService);
        assertNotNull(customerService);
        assertNotNull(purchaseService);
        assertNotNull(opinionService);
        assertNotNull(producerService);
        assertNotNull(productService);
        reloadDataService.reloadData();
    }

    @Test
    @DisplayName("Polecenie 9")
    void thatProductCanBeBoughtByCustomer() {
        //given
        final Customer customer = customerService.create(StoreFixtures.someCustomer());
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        productService.create(StoreFixtures.someProduct2(producer));

        final var before = purchaseService.findAll(customer.getEmail(), product1.getProductCode());

        //when
        shoppingCartService.makeAPurchase(customer.getEmail(), product1.getProductCode(), 10);

        //then
        final var after = purchaseService.findAll(customer.getEmail(), product1.getProductCode());
        assertEquals(before.size() + 1, after.size());

    }

}
