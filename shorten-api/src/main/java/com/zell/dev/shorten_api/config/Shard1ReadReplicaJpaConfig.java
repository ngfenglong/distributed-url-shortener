package com.zell.dev.shorten_api.config;

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
        basePackages = "com.zell.dev.shorten_api.repository.shard1.replica",
        entityManagerFactoryRef = "shard1ReadReplicaEntityManagerFactory",
        transactionManagerRef = "shard1ReadReplicaTransactionManager"
)
public class Shard1ReadReplicaJpaConfig {
    @Value("${sharding.replicaShards.1.url}")
    private String shard1ReplicaUrl;

    @Value("${sharding.replicaShards.0.username}")
    private String shard1ReplicaUsername;

    @Value("${sharding.replicaShards.0.password}")
    private String shard1ReplicaPassword;


    @Bean(name = "shard1ReadReplicaDataSource")
    public DataSource shard1ReadReplicaDataSource() {
        return DataSourceBuilder.create()
                .url(shard1ReplicaUrl)
                .username(shard1ReplicaUsername)
                .password(shard1ReplicaPassword)
                .build();
    }

    @Bean(name = "shard1ReadReplicaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean shard1ReadReplicaEntityManagerFactory(
            @Qualifier("shard1ReadReplicaDataSource") DataSource dataSource
    ) {
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);

        em.setPackagesToScan("com.zell.dev.common_lib.model");
        em.setPersistenceUnitName("shard1Replica");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean(name = "shard1ReadReplicaTransactionManager")
    public PlatformTransactionManager shard1TransactionManager(
            @Qualifier("shard1ReadReplicaEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}

