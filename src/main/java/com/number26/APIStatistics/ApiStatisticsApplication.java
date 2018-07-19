package com.number26.APIStatistics;

import com.number26.APIStatistics.dao.DataStore;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

@SpringBootApplication
public class ApiStatisticsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx =
			SpringApplication.run(ApiStatisticsApplication.class, args);
		String numberOfBuckets = ctx.getEnvironment().getProperty("timeInterval");
		DataStore.initializeStore(Integer.parseInt(numberOfBuckets));
	}
}
