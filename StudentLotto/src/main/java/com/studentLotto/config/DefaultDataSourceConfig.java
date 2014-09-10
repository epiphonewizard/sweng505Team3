package com.studentLotto.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.jdbc.datasource.lookup.JndiDataSourceLookup;

import javax.sql.DataSource;

@Configuration
@Profile("default")
class DefaultDataSourceConfig implements DataSourceConfig {

    @Bean
    @Override
    public DataSource dataSource() {
    	final JndiDataSourceLookup dsLookup = new JndiDataSourceLookup();
		dsLookup.setResourceRef(true);
		DataSource dataSource = dsLookup.getDataSource("StudentLottoDb");
		return dataSource;
    }
}
