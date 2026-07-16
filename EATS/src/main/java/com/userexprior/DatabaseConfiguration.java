package com.userexprior;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

/**
 * Database configuration for managing multiple datasources.
 * Configures primary and secondary datasources with their respective transaction managers.
 */
@Configuration
public class DatabaseConfiguration {

	/**
	 * Creates the primary datasource using properties from spring.datasource prefix.
	 * This is marked as @Primary to be used as default when no qualifier is specified.
	 * Properties are loaded from application.properties: spring.datasource.jdbcUrl, username, password
	 *
	 * @return DataSource configured for primary database (eatsdb)
	 */
	@Bean(name = "datasource1")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * Creates the secondary datasource using properties from spring.datasource.db prefix.
	 * Properties are loaded from application.properties: spring.datasource.db.jdbcUrl, username, password
	 *
	 * @return DataSource configured for secondary database
	 */
	@Bean(name = "datasource2")
	@ConfigurationProperties(prefix = "spring.datasource.db")
	public DataSource secondaryDataSource() {
		return DataSourceBuilder.create().build();
	}

	/**
	 * Transaction manager for the primary datasource.
	 * Manages transactional operations for the primary database (datasource1).
	 * Marked as @Primary so it's used by default for transaction management.
	 *
	 * @param datasource The primary datasource (datasource1)
	 * @return DataSourceTransactionManager for primary database
	 */
	@Bean(name = "tm1")
	@Primary
	DataSourceTransactionManager tm1(@Qualifier("datasource1") DataSource datasource) {
		DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
		return txm;
	}

	/**
	 * Transaction manager for the secondary datasource.
	 * Manages transactional operations for the secondary database (datasource2).
	 * Can be explicitly referenced using @Qualifier("tm2") where needed.
	 *
	 * @param datasource The secondary datasource (datasource2)
	 * @return DataSourceTransactionManager for secondary database
	 */
	@Bean(name = "tm2")
	DataSourceTransactionManager tm2(@Qualifier("datasource2") DataSource datasource) {
		DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
		return txm;
	}
}
