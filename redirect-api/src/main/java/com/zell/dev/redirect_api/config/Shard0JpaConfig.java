package com.zell.dev.redirect_api.config;

import jakarta.persistence.EntityManagerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.zell.dev.redirect_api.repository.shard0",
        entityManagerFactoryRef = "shard0EntityManagerFactory",
        transactionManagerRef = "shard0TransactionManager"
)
public class Shard0JpaConfig {
    @Value("${sharding.shards.0.url}")
    private String shard0Url;

    @Value("${sharding.shards.0.username}")
    private String shard0Username;

    @Value("${sharding.shards.0.password}")
    private String shard0Password;


    @Bean(name = "shard0DataSource")
    public DataSource shard0DataSource() {
        return DataSourceBuilder.create()
                .url(shard0Url)
                .username(shard0Username)
                .password(shard0Password)
                .build();
    }

    @Bean(name = "shard0EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean shard0EntityManagerFactory(
            @Qualifier("shard0DataSource") DataSource dataSource
    ) {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);

        // provide path to scan for entity
        em.setPackagesToScan("com.zell.dev.common_lib.model");
        em.setPersistenceUnitName("shard0");

        //  Configure Hibernate/JPA as the JPA
        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    // To use for transaction -> Committing or rolling back
    @Bean(name = "shard0TransactionManager")
    public PlatformTransactionManager shard0TransactionManager(
            @Qualifier("shard0EntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }

}
