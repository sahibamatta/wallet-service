package com.wallet.walletservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wallet.walletservice.entity.WalletProfilesEntity;

public interface WalletProfilesRepository extends CrudRepository<WalletProfilesEntity,Integer> {

	@Query(value="select w.walletAddress from WalletProfilesEntity w")
	List<String> getWalletAddresses();

}
