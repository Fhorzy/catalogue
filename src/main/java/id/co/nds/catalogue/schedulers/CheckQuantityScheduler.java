package id.co.nds.catalogue.schedulers;

import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import id.co.nds.catalogue.entities.ProductEntity;
import id.co.nds.catalogue.services.ProductService;

@Component
public class CheckQuantityScheduler {
    @Autowired
    ProductService productService;

    static final Logger logger = LogManager.getLogger(CronScheduler.class);
    Integer counterB = 0;

    @Scheduled(cron = "0 0/3 * * * ?") //every 10 seconds
    public void cronSchedule() throws Exception {
        Integer counterA = 0;
        logger.debug("Start Cron at " + Calendar.getInstance().getTime());
        logger.info("Counter-A: " + counterA);
        logger.info("Counter-B: " + counterB);
        counterA++;
        counterB++;

        List<ProductEntity> products = productService.findProductsLessThan5Quantity();
        for(int i = 0; i < products.size(); i++) {
            logger.info("Products no." + (i+1));
            logger.info("Products name:" + products.get(i).getName());
            logger.info("Products quantity:" + products.get(i).getQuantity());
        }
    }
}
