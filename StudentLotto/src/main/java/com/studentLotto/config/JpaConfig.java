package com.studentLotto.config;

import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import com.studentLotto.Application;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackageClasses = Application.class)
class JpaConfig implements TransactionManagementConfigurer {

    @Value("${hibernate.dialect}")
    private String dialect;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hbm2ddlAuto;

    @Bean
	public DataSource configureDataSource() {
		final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = dsLookup.getDataSource("StudentLottoDb");
		return dataSource;
	}

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean configureEntityManagerFactory() {
        LocalContainerEntityManagerFactoryBean entityManagerFactory = new LocalContainerEntityManagerFactoryBean();
        entityManagerFactory.setDataSource(configureDataSource());
        entityManagerFactory.setPackagesToScan("com.studentLotto");
        entityManagerFactory.setJpaVendorAdapter(new HibernateJpaVendorAdapter());

        Properties jpaProperties = new Properties();
        jpaProperties.put(org.hibernate.cfg.Environment.DIALECT, dialect);
        jpaProperties.put(org.hibernate.cfg.Environment.HBM2DDL_AUTO, hbm2ddlAuto);
        entityManagerFactory.setJpaProperties(jpaProperties);
 
        return entityManagerFactory;
    }

    @Bean(name="transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new JpaTransactionManager();
    }
}
