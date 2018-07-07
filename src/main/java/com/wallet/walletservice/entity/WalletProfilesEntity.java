package com.wallet.walletservice.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@Entity
@Table(name ="wallet_profiles")
public class WalletProfilesEntity implements Serializable{

	private static final long serialVersionUID = -4741124055137741873L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="wallet_address")
	private String walletAddress;
	private String name;
	private String phone;
	private String email;

}
