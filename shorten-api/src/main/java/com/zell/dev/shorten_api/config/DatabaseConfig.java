package com.zell.dev.shorten_api.config;

import com.zell.dev.common_service.config.routing.ShardRoutingDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

import static com.zell.dev.common_lib.constants.GlobalConstant.*;

@Configuration
@EnableJpaRepositories(
        basePackages = "com.zell.dev.shorten_api.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager"
)
public class DatabaseConfig {
    @Bean
    @ConfigurationProperties("shard.0")
    public DataSource shard0MasterDataSource() {
        return DataSourceBuilder.create().build();
    }

    // Shard 0 Replica
    @Bean
    @ConfigurationProperties("shard.0.replica")
    public DataSource shard0ReplicaDataSource() {
        return DataSourceBuilder.create().build();
    }

    // Shard 1 Master
    @Bean
    @ConfigurationProperties("shard.1")
    public DataSource shard1MasterDataSource() {
        return DataSourceBuilder.create().build();
    }

    // Shard 1 Replica
    @Bean
    @ConfigurationProperties("shard.1.replica")
    public DataSource shard1ReplicaDataSource() {
        return DataSourceBuilder.create().build();
    }


    @Bean(name = "routingDataSource")
    @Primary
    public DataSource routingDataSource() {
        ShardRoutingDataSource routingDataSource = new ShardRoutingDataSource();

        Map<Object, Object> dataSources = new HashMap<>();

        dataSources.put(generateLookUpKey(0, OPERATION_WRITE), shard0MasterDataSource());
        dataSources.put(generateLookUpKey(0, OPERATION_READ), shard0ReplicaDataSource());
        dataSources.put(generateLookUpKey(1, OPERATION_WRITE), shard1MasterDataSource());
        dataSources.put(generateLookUpKey(1, OPERATION_READ), shard1ReplicaDataSource());

        routingDataSource.setTargetDataSources(dataSources);
        routingDataSource.setDefaultTargetDataSource(shard0MasterDataSource());

        return routingDataSource;
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
        factory.setDataSource(routingDataSource());
        factory.setPackagesToScan("com.zell.dev.common_lib.model");
        factory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        return factory;
    }

    @Bean
    public JpaTransactionManager transactionManager() {
        return new JpaTransactionManager(entityManagerFactory().getObject());
    }

}