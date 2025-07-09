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
        basePackages = "com.zell.dev.redirect_api.repository.shard1",
        entityManagerFactoryRef = "shard1EntityManagerFactory",
        transactionManagerRef = "shard1TransactionManager"
)
public class Shard1JpaConfig {
    @Value("${sharding.shards.1.url}")
    private String shard1Url;

    @Value("${sharding.shards.1.username}")
    private String shard1Username;

    @Value("${sharding.shards.1.password}")
    private String shard1Password;


    @Bean(name = "shard1DataSource")
    public DataSource shard1DataSource() {
        return DataSourceBuilder.create()
                .url(shard1Url)
                .username(shard1Username)
                .password(shard1Password)
                .build();
    }

    @Bean(name = "shard1EntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean shard1EntityManagerFactory(
            @Qualifier("shard1DataSource") DataSource dataSource
    ) {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);

        em.setPackagesToScan("com.zell.dev.common_lib.model");
        em.setPersistenceUnitName("shard1");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        return em;
    }

    @Bean(name = "shard1TransactionManager")
    public PlatformTransactionManager shard1TransactionManager(
            @Qualifier("shard1EntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
