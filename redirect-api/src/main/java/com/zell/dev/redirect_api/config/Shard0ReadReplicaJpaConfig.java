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
        basePackages = "com.zell.dev.redirect_api.repository.shard0.replica",
        entityManagerFactoryRef = "shard0ReadReplicaEntityManagerFactory",
        transactionManagerRef = "shard0ReadReplicaTransactionManager"
)
public class Shard0ReadReplicaJpaConfig {
    @Value("${sharding.replicaShards.0.url}")
    private String shard0ReplicaUrl;

    @Value("${sharding.replicaShards.0.username}")
    private String shard0ReplicaUsername;

    @Value("${sharding.replicaShards.0.password}")
    private String shard0ReplicaPassword;


    @Bean(name = "shard0ReadReplicaDataSource")
    public DataSource shard0ReadReplicaDataSource() {
        return DataSourceBuilder.create()
                .url(shard0ReplicaUrl)
                .username(shard0ReplicaUsername)
                .password(shard0ReplicaPassword)
                .build();
    }

    @Bean(name = "shard0ReadReplicaEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean shard0ReadReplicaEntityManagerFactory(
            @Qualifier("shard0ReadReplicaDataSource") DataSource dataSource
    ){
        var em = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource);

        em.setPackagesToScan("com.zell.dev.common_lib.model");
        em.setPersistenceUnitName("shard0Replica");

        var vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);

        return em;
    }

    @Bean(name = "shard0ReadReplicaTransactionManager")
    public PlatformTransactionManager shard0TransactionManager(
            @Qualifier("shard0ReadReplicaEntityManagerFactory") EntityManagerFactory emf
    ) {
        return new JpaTransactionManager(emf);
    }
}
