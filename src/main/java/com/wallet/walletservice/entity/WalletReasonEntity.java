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
@Table(name ="wallet_reason_for_transfer")
public class WalletReasonEntity implements Serializable{

	private static final long serialVersionUID = 6610518507613406202L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	int id;
	String reasons;
}
