package pl.zajavka.buisness;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.OngoingStubbing;
import pl.zajavka.domain.*;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OpinionServiceTest {

    @InjectMocks
    private OpinionService opinionService;

    @Mock
    private PurchaseService purchaseService;

    @Mock
    private OpinionRepository opinionRepository;

    @Test
    @DisplayName("polecenia 5 cz.1")
    void thatOpinionCanBeCreatedForProductThatCustomerAlreadyBought() {
        //given
        final var customer = StoreFixtures.someCustomer();
        final var producer = StoreFixtures.someProducer();
        final var product = StoreFixtures.someProduct1(producer);
        final var purchase = StoreFixtures.somePurchase(customer, product);
        final var opinion = StoreFixtures.someOpinion(customer, product);

        when(purchaseService.findAll(customer.getEmail(), product.getProductCode()))
                .thenReturn(List.of(purchase.withId(1L)));
        when(opinionRepository.create(opinion)).thenReturn(opinion.withId(10L));

        //when
        Opinion result = opinionService.create(opinion);

        //then
        verify(opinionRepository).create(opinion);
        Assertions.assertEquals(opinion.withId(10L), result);


    }

    @Test
    @DisplayName("polecenia 5 cz.2")
    void thatOpinionCanBeCreatedForProductThatCustomerDidNodBought() {
        //given
        final var customer = StoreFixtures.someCustomer();
        final var producer = StoreFixtures.someProducer();
        final var product = StoreFixtures.someProduct1(producer);
        final var opinion = StoreFixtures.someOpinion(customer, product);

        when(purchaseService.findAll(customer.getEmail(), product.getProductCode()))
                .thenReturn(List.of());

        // when

        Throwable exception = Assertions.assertThrows(RuntimeException.class, () -> opinionService.create(opinion));
        Assertions.assertEquals(
                "Customer: [%s] wants to give opinion for product : [%s] but there is no purchase"
                        .formatted(customer.getEmail(), product.getProductCode()),
                exception.getMessage()
        );

        verify(opinionRepository, never()).create(any(Opinion.class));

    }




}