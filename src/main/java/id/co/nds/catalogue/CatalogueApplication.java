package id.co.nds.catalogue;

import javax.annotation.PreDestroy;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import id.co.nds.catalogue.schedulers.DbParamScheduler;

@SpringBootApplication
@EnableScheduling
public class CatalogueApplication {
	static final Logger logger = LogManager.getLogger(CatalogueApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(CatalogueApplication.class, args);
	}

	@PreDestroy
	public void destroy() {
		logger.info("");
		logger.info("Stopping Configuration for System.....");

		logger.info("Stopping custom DB scheduler....");
		DbParamScheduler.shutdownScheduler();

		logger.info("");
		logger.info("Finish Stopping Configuration for System....");
	}

}
