package pl.zajavka.integration;

import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import pl.zajavka.buisness.*;
import pl.zajavka.domain.*;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;

import java.time.LocalDate;
import java.time.ZoneOffset;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringJUnitConfig(classes = ApplicationConfiguration.class)
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class CustomerServiceTest {
    private ReloadDataService reloadDataService;
    private CustomerService customerService;
    private PurchaseService purchaseService;
    private OpinionService opinionService;

    private ProductService productService;
    private ProducerService producerService;


    @BeforeEach
    public void setUp() {
        assertNotNull(reloadDataService);
        assertNotNull(customerService);
        assertNotNull(purchaseService);
        assertNotNull(opinionService);
        assertNotNull(producerService);
        assertNotNull(productService);
        reloadDataService.loadRandomData();
    }

    @Test
    @DisplayName("Polecenia 4 - cz.1")
    void thatCustomerIsRemovedCorrectly() {

        //given
        final Customer customer = customerService.create(StoreFixtures.someCustomer());
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
        purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
        purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
        opinionService.create(StoreFixtures.someOpinion(customer, product1));

        assertEquals(customer, customerService.find(customer.getEmail()));

        //when - usuwam klienta

        customerService.remove(customer.getEmail());

        //then - przy próbie znalezienia wyrzuca wyjatek, usunięcie klienta wyrzuciło też wpisy o jego zakupach i opiniach

        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.find(customer.getEmail()));
        assertEquals("Customer with email [%s] is missing".formatted(customer.getEmail()), exception.getMessage());

        assertTrue(purchaseService.findAll(customer.getEmail()).isEmpty());
        assertTrue(opinionService.findAll(customer.getEmail()).isEmpty());


    }

    @Test
    @DisplayName("Polecenia 4 - cz.2")
    void thatPurchaseAndOpinionIsNotRemovedWhenCustomerRemovingFails() {
        //given
        final Customer customer = customerService.create(StoreFixtures.someCustomer().withDateOfBirth(LocalDate.of(1950, 10, 4)));
        final Producer producer = producerService.create(StoreFixtures.someProducer());
        final Product product1 = productService.create(StoreFixtures.someProduct1(producer));
        final Product product2 = productService.create(StoreFixtures.someProduct2(producer));
        Purchase purchase1 = purchaseService.create(StoreFixtures.somePurchase(customer, product1).withQuantity(1));
        Purchase purchase2 = purchaseService.create(StoreFixtures.somePurchase(customer, product2).withQuantity(3));
        Opinion opinion1 = opinionService.create(StoreFixtures.someOpinion(customer, product1));

        assertEquals(customer, customerService.find(customer.getEmail()));

        //when - klienta nie udaje się usunac bo ejst starszy niz 40 lat
        RuntimeException exception = assertThrows(RuntimeException.class, () -> customerService.remove(customer.getEmail()));
        assertEquals("Could now remove cutomer because he/she is older than 40, email: [%s]".formatted(customer.getEmail())
                , exception.getMessage()
        );

        assertEquals(customer, customerService.find(customer.getEmail()));
        assertEquals(
                 List.of(
                        purchase1
                                .withCustomer(Customer.builder().id(customer.getId()).build())
                                .withProduct(Product.builder().id(product1.getId()).build())
                                .withDateTime(purchase1.getDateTime().withOffsetSameInstant(ZoneOffset.UTC)),
                        purchase2
                                .withCustomer(Customer.builder().id(customer.getId()).build())
                                .withProduct(Product.builder().id(product2.getId()).build())
                                .withDateTime(purchase2.getDateTime().withOffsetSameInstant(ZoneOffset.UTC))
                ),
                purchaseService.findAll(customer.getEmail())
        );
        assertEquals( List.of(
                        opinion1
                                .withCustomer(Customer.builder().id(customer.getId()).build())
                                .withProduct(Product.builder().id(product1.getId()).build())
                                .withDateTime(opinion1.getDateTime().withOffsetSameInstant(ZoneOffset.UTC))
                ),
                opinionService.findAll(customer.getEmail())
        );
    }

    @Test
    @DisplayName("Polecenie8")
    void thatCustomersGivingUnwantedOpinionsAreRemoved() {
        //given
        reloadDataService.reloadData();
        assertEquals(100, customerService.findAll().size());

        //when

        customerService.removeUnwantedCustomers();

        //then

        assertEquals(64, customerService.findAll().size());
    }

}
