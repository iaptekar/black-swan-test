package com.blackswan;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

@org.springframework.context.annotation.Configuration
public class Configuration {

	@Bean
	public DataSource dataSource() throws Exception {
		return new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL).setName("userdb").build();
	}
}
