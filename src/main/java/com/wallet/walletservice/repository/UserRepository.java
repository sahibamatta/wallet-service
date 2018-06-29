package com.wallet.walletservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.wallet.walletservice.entity.UserEntity;

public interface UserRepository extends CrudRepository<UserEntity,Integer> {

	UserEntity findTop1ByEmailIdOrUserId(String email,String userId);
	@Query("select u.userId from UserEntity u")
	List<String> getUserIds();
	UserEntity findByUserId(String userId);
	@Transactional
	@Modifying
	@Query("delete from UserEntity u where u.userId = ?1")
	void deleteUsersByUserId(String id);
	UserEntity findByUserIdAndPassword(String userId,String password);

}
