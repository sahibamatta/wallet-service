package com.wallet.walletservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@Entity
@Table(name ="wallet_currency_details")
@ToString
public class WalletCurrencyDetailsEntity implements Serializable {
	
	private static final long serialVersionUID = 3095060792356769096L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Date date;
	private int pyg;
	private Double eth;

}
