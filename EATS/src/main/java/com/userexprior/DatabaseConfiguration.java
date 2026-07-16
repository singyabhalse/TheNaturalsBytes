package com.userexprior;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Configuration class for setting up multiple data sources in the application.
 * This allows the application to connect to different databases (primary and secondary).
 */
@Configuration
public class DatabaseConfiguration {

	/**
	 * Primary data source configured with properties from "spring.datasource" prefix.
	 * This is marked as @Primary, making it the default data source for the application.
	 */
	@Bean(name = "datasource1")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * Secondary data source configured with properties from "spring.datasource.db" prefix.
	 * This data source can be used for specific components that need a different database connection.
	 */
	@Bean(name = "datasource2")
	@ConfigurationProperties(prefix = "spring.datasource.db")
	public DataSource secondaryDataSource() {
		return DataSourceBuilder.create().build();
	}

//	@Primary
//	@Bean(name = "datasource1EntityManagerFactory")
//	public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder,
//			@Qualifier("datasource1") DataSource dataSource) {
//
//		return builder.dataSource(dataSource).packages("com.userexprior").build();
//	}

	/**
	 * Transaction manager for the primary data source.
	 * Handles transaction management for database operations on datasource1.
	 * Marked as @Primary since it's the default transaction manager.
	 */
	@Bean(name = "tm1")
	@Autowired
	@Primary
	DataSourceTransactionManager tm1(@Qualifier("datasource1") DataSource datasource) {
		DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
		return txm;
	}

	/**
	 * Transaction manager for the secondary data source.
	 * Handles transaction management for database operations on datasource2.
	 * Use @Transactional(transactionManager = "tm2") to use this manager in specific methods.
	 */
	@Bean(name = "tm2")
	@Autowired
	DataSourceTransactionManager tm2(@Qualifier("datasource2") DataSource datasource) {
		DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
		return txm;
	}
}
