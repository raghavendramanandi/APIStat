package com.number26.APIStatistics;

import com.number26.APIStatistics.dao.DataStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
public class ApiStatisticsApplication {

	public static void main(String[] args) {
		ConfigurableApplicationContext ctx =
			SpringApplication.run(ApiStatisticsApplication.class, args);
		String numbberOfbuckets = ctx.getEnvironment().getProperty("numbberOfbuckets");
		DataStore.InitializeStore(Integer.parseInt(numbberOfbuckets));
	}
}
