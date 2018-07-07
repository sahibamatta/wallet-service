package com.wallet.walletservice.repository;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import com.wallet.walletservice.entity.WalletCurrencyDetailsEntity;

public interface WalletCurrencyDetailsRepository extends CrudRepository<WalletCurrencyDetailsEntity,Integer>{
	
	WalletCurrencyDetailsEntity findByDate(Date date);

}
