package com.voicemate.usermanagementservice.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import jakarta.persistence.EntityManagerFactory;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.voicemate.usermanagementservice.repository.db2repo", entityManagerFactoryRef = "db2EntityManagerFactory", transactionManagerRef = "db2TransactionManager")
public class Db2ConnectionConfig {

	@Bean(name = "db2")
	@ConfigurationProperties(prefix = "spring.datasource.db2")
	DataSource db2DataSource() {
		return DataSourceBuilder.create().build();

	}

	@Bean(name = "db2EntityManagerFactory")
	LocalContainerEntityManagerFactoryBean db2EntityManagerFactory(EntityManagerFactoryBuilder builder,
			@Qualifier("db2") DataSource dataSource) {
		return builder.dataSource(dataSource).packages("com.voicemate.usermanagementservice.entities.db2entity")
				.persistenceUnit("db2").build();

	}

	@Bean(name = "db2TransactionManager")
	PlatformTransactionManager db2TransactionMana(
			@Qualifier("db2EntityManagerFactory") EntityManagerFactory entityManagerFactory) {
		return new JpaTransactionManager(entityManagerFactory);

	}

}
