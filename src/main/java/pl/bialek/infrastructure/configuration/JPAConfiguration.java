package pl.bialek.infrastructure.configuration;

import jakarta.persistence.EntityManagerFactory;
import lombok.AllArgsConstructor;
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.Location;
import org.flywaydb.core.api.configuration.ClassicConfiguration;
import org.hibernate.cfg.Environment;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.PropertySource;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import pl.bialek.infrastructure.database.entity._EntityMarker;
import pl.bialek.infrastructure.database.repository.jpa._JpaMarker;

import javax.sql.DataSource;
import java.util.Objects;
import java.util.Properties;

@Configuration
@AllArgsConstructor
@EnableJpaRepositories(basePackageClasses = _JpaMarker.class)
@PropertySource({"classpath:database.properties"})
@EnableTransactionManagement
public class JPAConfiguration {

    private final org.springframework.core.env.Environment environment;

    @Bean
    @DependsOn("flyway")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactoryBean = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactoryBean.setDataSource(dataSource());
        entityManagerFactoryBean.setPackagesToScan(_EntityMarker.class.getPackageName());
        entityManagerFactoryBean.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        entityManagerFactoryBean.setJpaProperties(jpaProperties());
        return entityManagerFactoryBean;
    }

    private Properties jpaProperties() {
        final Properties properties = new Properties();

        properties.setProperty(Environment.DIALECT, environment.getProperty(Environment.DIALECT));
        properties.setProperty(Environment.HBM2DDL_AUTO, environment.getProperty(Environment.HBM2DDL_AUTO));
        properties.setProperty(Environment.SHOW_SQL, environment.getProperty(Environment.SHOW_SQL));
        properties.setProperty(Environment.FORMAT_SQL, environment.getProperty(Environment.FORMAT_SQL));
        return properties;
    }

    @Bean
    public PlatformTransactionManager transactionManager(final EntityManagerFactory entityManagerFactory) {
        final JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory);
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslator() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

    @Bean(initMethod = "migrate")
    Flyway flyway() {
        ClassicConfiguration configuration = new ClassicConfiguration();
        configuration.setBaselineOnMigrate(true);
        configuration.setLocations(new Location("filesystem:src/main/resources/flyway/migrations"));
        configuration.setDataSource(dataSource());
        return new Flyway(configuration);
    }

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driverClassName")));
        dataSource.setUrl(environment.getProperty("jdbc.url"));
        dataSource.setUsername(environment.getProperty("jdbc.user"));
        dataSource.setPassword(environment.getProperty("jdbc.pass"));
        return dataSource;
    }



//    public DataSource dataSource() {
//        HikariConfig hikariConfig = new HikariConfig();
//        hikariConfig.setDriverClassName(Objects.requireNonNull(environment.getProperty("jdbc.driverClassName")));
//        hikariConfig.setJdbcUrl(environment.getProperty("jdbc.url"));
//        hikariConfig.setUsername(environment.getProperty("jdbc.user"));
//        hikariConfig.setPassword(environment.getProperty("jdbc.pass"));
//
//        hikariConfig.setConnectionTestQuery("SELECT 1");
//        hikariConfig.setPoolName("springHikariCP");
//
//        // Map.entry("hibernate.hikari.maximumPoolSize", "20"),
//        hikariConfig.setMaximumPoolSize(20);
//        // Map.entry("hibernate.hikari.connectionTimeout", "20000"),
//        hikariConfig.setConnectionTimeout(20000);
//        // Map.entry("hibernate.hikari.minimumIdle", "10"),
//        hikariConfig.setMinimumIdle(10);
//        // Map.entry("hibernate.hikari.idleTimeout", "300000")
//        hikariConfig.setIdleTimeout(300000);
//
//        return new HikariDataSource(hikariConfig);
//    }
}

