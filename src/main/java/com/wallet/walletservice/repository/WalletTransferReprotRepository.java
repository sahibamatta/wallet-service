package com.wallet.walletservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.wallet.walletservice.entity.WalletTransferReportEntity;

public interface WalletTransferReprotRepository extends CrudRepository<WalletTransferReportEntity,Integer>{

	@Query(value="select w from WalletTransferReportEntity w where w.date between ?1 and ?2")
	List<WalletTransferReportEntity> getDateBetween(Date start,Date end);

}
