package com.wallet.walletservice.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import com.wallet.walletservice.properties.WalletProperties;


@Configuration
@PropertySource(value="classpath:wallet.properties")
public class WalletConfig {

	@Autowired
	private Environment environment;

	
	@Bean
	public WalletProperties appProperties() {
		WalletProperties bean = new WalletProperties();
		bean.setWalletAddress(environment.getProperty("wallet.address"));
		return bean;
	}
	
}
