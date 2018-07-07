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
@Table(name ="wallet_terms_and_conditions")
public class WalletTermsAndConditions implements Serializable{
	
	
	private static final long serialVersionUID = 1914691724975716990L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String title;
	private String body;

}
