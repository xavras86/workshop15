package pl.zajavka.buisness;

import org.springframework.stereotype.Service;
import pl.zajavka.domain.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Random;
import java.util.stream.IntStream;

@Service
public class RandomDataPreparationService {
    Customer createCustomer() {
        String name = randomString(65, 90, 1) + randomString(97, 122, 10) + randomString(48, 57, 2);
        return Customer.builder()
                .userName(name + "user")
                .email(name + "@gmail.com")
                .name(name)
                .surname("surname")
                .dateOfBirth(LocalDate.of(1991, 9, 2))
                .telephoneNumber("+" + randomString(48, 57, 11))
                .build();
    }

    Opinion createOpinion(Customer customer, Product product) {
        return Opinion.builder()
                .customer(customer)
                .product(product)
                .stars((byte) 4)
                .comment("My comment")
                .dateTime(OffsetDateTime.of(2020, 1, 1, 12, 9, 10, 1, ZoneOffset.ofHours(4)))
                .build();
    }

    Producer createProducer() {
        return Producer.builder()
                .producerName(randomString(65, 90, 1) + randomString(97, 122, 10))
                .address("someAddress")
                .build();
    }

    Product createProduct(Producer producer) {
        return Product.builder()
                .productCode(randomString(65, 90, 3) + randomString(97, 122, 4) + randomString(48, 57, 2))
                .productName("productName")
                .productPrice(BigDecimal.valueOf(162.16))
                .adultsOnly(false)
                .description("someDescription")
                .producer(producer)
                .build();
    }

    Purchase createPurchase(Customer customer, Product product) {
        return Purchase.builder()
                .customer(customer)
                .product(product)
                .quantity(randomInt(1, 5))
                .dateTime(OffsetDateTime.of(2020, 1, 1, 10, 9, 10, 1, ZoneOffset.ofHours(4)))
                .build();
    }

    private String randomString(int min, int max, int lenght) {

        return IntStream.range(0, lenght)
                .boxed()
                .reduce("", (previous, next) -> previous + (char) randomInt(min, max), String::concat);
    }

    private int randomInt(int min, int max) {
        return new Random().nextInt(max - min) + min;
    }
}
