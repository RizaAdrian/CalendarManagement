package com.uaic.main.config;

import java.util.Properties;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
public class DatasourceConfig {

  @Value("${datasource.url}")
  private String url;
  @Value("${datasource.driver}")
  private String driver;
  @Value("${datasource.user}")
  private String user;
  @Value("${datasource.password}")
  private String password;

  @Bean(name = "dataSource")
  public DriverManagerDataSource dataSource() {
    DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
    driverManagerDataSource.setDriverClassName(driver);
    driverManagerDataSource.setUrl(url);
    driverManagerDataSource.setUsername(user);
    driverManagerDataSource.setPassword(password);
    driverManagerDataSource.setConnectionProperties(properties());
    return driverManagerDataSource;
  }

  private Properties properties() {
    Properties properties = new Properties();
    return properties;
  }

}
