package id.co.nds.catalogue.schedulers;

import java.util.Calendar;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class FixedRateScheduler {
    static final Logger logger = LogManager.getLogger(FixedRateScheduler.class);
    Integer counterB = 0;

    // @Scheduled(fixedRate = 10000) //every 10 seconds
    public void fixedRateSchedule() throws Exception {
        Integer counterA = 0;
        logger.debug("Start FixedRateScheduler at " + Calendar.getInstance().getTime());
        logger.info("Counter-A: " + counterA);
        logger.info("Counter-B: " + counterB);
        counterA++;
        counterB++;
    }

    // @Scheduled(fixedRateString = "${param.scheduler.fixedrate.ms}") //every 10 seconds
    public void fixedRateParamSchedule() throws Exception {
        Integer counterA = 0;
        logger.debug("Start FixedRateScheduler at " + Calendar.getInstance().getTime());
        logger.info("Counter-A: " + counterA);
        logger.info("Counter-B: " + counterB);
        counterA++;
        counterB++;

        Thread.sleep(15000);
    }
}
