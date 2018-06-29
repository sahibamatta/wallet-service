package com.wallet.walletservice.repository;

import org.springframework.data.repository.CrudRepository;

import com.wallet.walletservice.entity.UserProfileEntity;

public interface UserProfileRepository  extends CrudRepository<UserProfileEntity,Integer>{

}
