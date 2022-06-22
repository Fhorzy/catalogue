package id.co.nds.catalogue.schedulers;

import java.util.TimeZone;
import java.util.concurrent.ScheduledFuture;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

import id.co.nds.catalogue.repos.ParamRepo;

@Component
public class DbParamScheduler implements SchedulingConfigurer {

    @Autowired
    private ParamRepo paramRepo;

    private static final String PARAM_KEY = "CRON_10_Seconds";

    Integer counter = 0;

    private static ScheduledTaskRegistrar scheduledTaskRegistrar;
    @SuppressWarnings("rawtypes")
    private static ScheduledFuture future;

    static final Logger logger = LogManager.getLogger(DbParamScheduler.class);
    private static String cronVal = "";
    public static boolean stopScheduler = false;

    @Bean
    public TaskScheduler poolScheduler() {

        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("DbParamaterScheduler-ThreadPoolTaskSchedule - ##");
        scheduler.setPoolSize(2); // paralel
        scheduler.initialize();
        return scheduler;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {

        if (scheduledTaskRegistrar == null) {
            scheduledTaskRegistrar = taskRegistrar;
        }
        if (taskRegistrar.getScheduler() == null) {
            taskRegistrar.setScheduler(poolScheduler());
        }

        reloadParamScheduler();

        CronTrigger croneTrigger = new CronTrigger(cronVal, TimeZone.getDefault());

        future = taskRegistrar.getScheduler().schedule(() -> scheduleCronTask(), croneTrigger);
    }

    public void scheduleCronTask() {

        logger.debug("scheduleCron: run scheduler with configuration -> {" + cronVal + "}...");

        try {
            counter++;
            logger.info("executing task......");
            logger.info("task " + counter);
            /*
             * here, put the business logic.
             */

            // call serviceGantiStatusCicilanAktif
            // call serviceGantiStatusCicJatuhTempo
            // call serviceGantiStatusCicTerlambat
            // call serviceHitungDenda

            logger.info("finish executing task......");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (stopScheduler) {
                cancelTasks(true);
                scheduledTaskRegistrar = null;
                logger.debug("Stopping scheduler Task...");

            }
        }

        reloadParamScheduler();

    }

    private void reloadParamScheduler() {

        if (cronVal.trim().equalsIgnoreCase("")) {
            cronVal = paramRepo.findById(PARAM_KEY).orElse(null).getParamValue();
        } else {
            String newCronFromDb = "";
            newCronFromDb = paramRepo.findById(PARAM_KEY).orElse(null).getParamValue();

            if (!stopScheduler && !newCronFromDb.equalsIgnoreCase(cronVal)) {
                cronVal = newCronFromDb;

                // reload new scheduler
                logger.info("scheduleCron: Next execution time of this taken from cron expression -> {" + newCronFromDb
                        + "}");
                cancelTasks(false);
                activateScheduler();
            }
        }
    }

    /**
     * @param mayInterruptIfRunning {@code true} if the thread executing this task
     *                              should be interrupted; otherwise, in-progress
     *                              tasks are allowed to complete
     */
    public void cancelTasks(boolean mayInterruptIfRunning) {
        logger.info("###Cancelling all tasks...");
        future.cancel(mayInterruptIfRunning); // set to false if you want the running task to be completed
                                              // first.
    }

    public void activateScheduler() {
        logger.info("###Reload Scheduler..");
        configureTasks(scheduledTaskRegistrar);
    }

    public static void shutdownScheduler() {
        logger.info("###ShuttingDown Scheduler..");
        stopScheduler = true;
    }

}