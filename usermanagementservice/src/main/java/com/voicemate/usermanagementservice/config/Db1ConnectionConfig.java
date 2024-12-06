package com.voicemate.usermanagementservice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import jakarta.persistence.EntityManagerFactory;

//@Configuration
//@EnableTransactionManagement
//@EnableJpaRepositories
public class Db1ConnectionConfig {

	@Primary
	@Bean(name = "db1")
	@ConfigurationProperties(prefix = "spring.datasource.db1")
	DataSource primaryDataSource() {
		return DataSourceBuilder.create().build();

	}

	@Primary
	@Bean(name = "db1EntityManagerFactory")
	LocalContainerEntityManagerFactoryBean primaryEntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("db1") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.voicemate.usermanagementservice.repository")
				.persistenceUnit("primary").build();

	}

	@Primary
	@Bean(name = "db1TransactionManager")
	PlatformTransactionManager primaryTransactionMana(
			@Qualifier("db1EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);

	}

	/*
	 * The primaryDataSource bean is the connection to the primary database. The
	 * primaryEntityManagerFactory bean is used to create the EntityManager for the
	 * primary database, which handles all JPA operations (including native
	 * queries). The primaryTransactionManager is used for managing transactions for
	 * the primary database. To execute a native query, inject EntityManager and use
	 * createNativeQuery to execute your custom SQL queries.
	 */

}
