package com.wallet.walletservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name="user_profiles")
public class UserProfileEntity implements Serializable{
	
	private static final long serialVersionUID = -463734443346643199L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String profile;
	
}
