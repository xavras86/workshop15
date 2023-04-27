package pl.zajavka.buisness;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.zajavka.domain.Customer;
import pl.zajavka.domain.Product;
import pl.zajavka.domain.Purchase;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@AllArgsConstructor
public class ShoppingCartService {

    private static final DateTimeFormatter DATE_TIME_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");

    private final CustomerService customerService;
    private final ProductService productService;
    private final PurchaseService purchaseService;


    @Transactional
    public void makeAPurchase(String email, String productCode, int quantity) {

        Customer customer = customerService.find(email);
        Product product = productService.find(productCode);
        Purchase purchase = purchaseService.create(
                Purchase.builder()
                        .customer(customer)
                        .product(product)
                        .quantity(quantity)
                        .dateTime(OffsetDateTime.of(2020, 1, 1, 12, 9, 10, 0, ZoneOffset.ofHours(4)))
                        .build()


        );
        log.info("customer: [{}] made a purchase for product: [{}], quantity: [{}]", email, productCode, quantity);
        log.debug("customer: [{}] made a purchase for product: [{}], quantity: [{}], purchase: [{}]", email, productCode, quantity, purchase);


    }
}
