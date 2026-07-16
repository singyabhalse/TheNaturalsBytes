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

@Configuration
public class DatabaseConfiguration {

	@Bean(name = "datasource1")
	@Primary
	@ConfigurationProperties(prefix = "spring.datasource")
	public DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();
	}

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

	@Bean(name = "tm1")
	@Autowired
	@Primary
	DataSourceTransactionManager tm1(@Qualifier("datasource1") DataSource datasource) {
		DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
		return txm;
	}

	@Bean(name = "tm2")
	@Autowired
	DataSourceTransactionManager tm2(@Qualifier("datasource2") DataSource datasource) {
		DataSourceTransactionManager txm = new DataSourceTransactionManager(datasource);
		return txm;
	}
}
