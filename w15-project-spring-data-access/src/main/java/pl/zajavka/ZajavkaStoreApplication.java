package pl.zajavka;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Service;
import pl.zajavka.buisness.OpinionService;
import pl.zajavka.buisness.ReloadDataService;
import pl.zajavka.buisness.ShoppingCartService;
import pl.zajavka.domain.Customer;
import pl.zajavka.domain.Product;
import pl.zajavka.infrastructure.configuration.ApplicationConfiguration;
import pl.zajavka.infrastructure.database.OpinionDatabaseRepository;


public class ZajavkaStoreApplication {
    public static void main(String[] args) {

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ApplicationConfiguration.class);
        ReloadDataService reloadDataService = applicationContext.getBean(ReloadDataService.class);
        OpinionService opinionService = applicationContext.getBean((OpinionService.class));
        OpinionDatabaseRepository opinionDatabaseRepository = applicationContext.getBean((OpinionDatabaseRepository.class));
        ShoppingCartService shoppingCartService = applicationContext.getBean(ShoppingCartService.class);
        reloadDataService.reloadData();

       // opinionService.findAll().forEach(a -> System.out.println(a));
        opinionService.removeUnwantedOpinions();

        System.out.println("OLNY 4,5 STARS     OLNY 4,5 STARS     OLNY 4,5 STARS     OLNY 4,5 STARS     ");
     //   opinionService.findAll().forEach(a -> System.out.println(a));

        shoppingCartService.makeAPurchase("btiffney6@spotify.com","68084-618",23 );



    }
}
