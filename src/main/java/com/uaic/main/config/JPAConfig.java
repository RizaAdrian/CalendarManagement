package com.uaic.main.config;

import java.util.Properties;

import javax.sql.DataSource;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.support.ClasspathScanningPersistenceUnitPostProcessor;
import org.springframework.data.jpa.support.MergingPersistenceUnitManager;
import org.springframework.data.web.config.EnableSpringDataWebSupport;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.persistenceunit.DefaultPersistenceUnitManager;
import org.springframework.orm.jpa.persistenceunit.PersistenceUnitPostProcessor;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@EnableSpringDataWebSupport
@EntityScan(basePackages = "com.uaic")
@EnableJpaRepositories(basePackages = "com.uaic",
    entityManagerFactoryRef = "entityManagerFactory")
@PropertySource("classpath:/application.properties")
public class JPAConfig {

  private static final String PACKAGE_TO_SCAN = "com.uaic";
  private static final String DATABASE_DIALECT = "org.hibernate.dialect.PostgreSQLDialect";
  private static final String PERSISTENCE_UNIT_NAME = "calendar";

  @Autowired
  private DataSource dataSource;
  @Autowired
  private JpaVendorAdapter jpaVendorAdapter;

  @Bean
  public PersistenceUnitPostProcessor persistenceUnitPostProcessor() {
    return new ClasspathScanningPersistenceUnitPostProcessor(PACKAGE_TO_SCAN);
  }

  @Bean
  public JpaVendorAdapter jpaVendorAdapter() {
    HibernateJpaVendorAdapter vendor = new HibernateJpaVendorAdapter();
    vendor.setDatabasePlatform(DATABASE_DIALECT);
    vendor.setGenerateDdl(false);
    vendor.setShowSql(true);
    return vendor;
  }

  @Bean(name = "entityManagerFactory")
  public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
    LocalContainerEntityManagerFactoryBean bean = new LocalContainerEntityManagerFactoryBean();
    bean.setDataSource(dataSource);
    bean.setJpaVendorAdapter(jpaVendorAdapter);
    bean.setPersistenceUnitManager(persistenceUnitManager());
    bean.setPersistenceUnitName(PERSISTENCE_UNIT_NAME);
    bean.setJpaProperties(getJpaProperties());
    return bean;
  }

  private Properties getJpaProperties() {
    Properties props = new Properties();
    return props;
  }

  @Bean
  public PlatformTransactionManager transactionManager() {
    return new JpaTransactionManager(entityManagerFactory().getObject());
  }

  @Bean
  public DefaultPersistenceUnitManager persistenceUnitManager() {
    DefaultPersistenceUnitManager bean = new MergingPersistenceUnitManager();
    bean.setPersistenceUnitPostProcessors(persistenceUnitPostProcessor());
    bean.setPersistenceXmlLocations("classpath*:META-INF/persistence.xml");
    bean.setDefaultDataSource(dataSource);
    bean.setDefaultPersistenceUnitName(PERSISTENCE_UNIT_NAME);
    return bean;
  }
  
  @Bean
  public Validator validator() {
      return new org.springframework.validation.beanvalidation.LocalValidatorFactoryBean();
  }

}
