package com.wallet.walletservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wallet.walletservice.entity.WalletReasonEntity;

public interface WalletReasonRepository extends CrudRepository<WalletReasonEntity, Integer>{
	
	@Query("select w.reasons from WalletReasonEntity w")
	List<String> getReasons();

}
