package com.wallet.walletservice.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.wallet.walletservice.entity.BlockedUserReportEntity;

public interface BlockedUserReportRepository extends CrudRepository<BlockedUserReportEntity,Integer>{
	
	public List<BlockedUserReportEntity> findByDate(Date date);
	public BlockedUserReportEntity findByUserId(String userId);

}
