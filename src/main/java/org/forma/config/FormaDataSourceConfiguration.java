package org.forma.config;

import javax.sql.DataSource;

import org.forma.model.Codif;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariDataSource;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "org.forma.repo", entityManagerFactoryRef = "entityManagerFactory", transactionManagerRef = "transactionManager")
public class FormaDataSourceConfiguration
{

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.forma")
    public DataSourceProperties formaDataSourceProperties()
    {
        return new DataSourceProperties();
    }

    @Bean
    @Primary
    @ConfigurationProperties("app.datasource.forma.configuration")
    public DataSource formaDataSource()
    {
        return formaDataSourceProperties().initializeDataSourceBuilder().type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "formaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean formaEntityManagerFactory(EntityManagerFactoryBuilder builder)
    {
        return builder.dataSource(formaDataSource()).packages(Codif.class).build();
    }

    @Primary
    @Bean
    public PlatformTransactionManager formaTransactionManager(
            final @Qualifier("formaEntityManagerFactory") LocalContainerEntityManagerFactoryBean formaEntityManagerFactory)
    {
        return new JpaTransactionManager(formaEntityManagerFactory.getObject());
    }

}