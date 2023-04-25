package pl.zajavka.infrastructure.database;

import org.springframework.stereotype.Component;
import pl.zajavka.domain.Opinion;
import pl.zajavka.domain.Product;
import pl.zajavka.domain.Purchase;

import java.time.format.DateTimeFormatter;
import java.util.Map;

@Component
public class DatabaseMapper {

    private static final DateTimeFormatter DATE_TIME_FORMAT
            = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ssX");

    public Map<String, ?> mapProduct(Product product) {
        return Map.of(
                "product_code", product.getProductCode(),
                "product_name", product.getProductName(),
                "product_price", product.getProductPrice(),
                "adults_only", product.getAdultsOnly(),
                "description", product.getDescription(),
                "producer_id", product.getProducer().getId()
        );
    }

    public Map<String, ?> mapPurchase(Purchase purchase) {
        return Map.of(
                "customer_id", purchase.getCustomer().getId(),
                "product_id", purchase.getProduct().getId(),
                "quantity", purchase.getQuantity(),
                "date_time", DATE_TIME_FORMAT.format(purchase.getDateTime())
        );
    }

    public Map<String, ?> mapOpinion(Opinion opinion) {
        return Map.of(
                "customer_id", opinion.getCustomer().getId(),
                "product_id", opinion.getProduct().getId(),
                "stars", opinion.getStars(),
                "comment", opinion.getComment(),
                "date_time", DATE_TIME_FORMAT.format(opinion.getDateTime())
        );
    }
}
