package com.wallet.walletservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
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
@Table(name="wallet_transfer_report")
public class WalletTransferReportEntity implements Serializable {

	private static final long serialVersionUID = -2885705117987069229L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name ="transfer_from_address")
	private String transferFromAddress;
	@Column(name ="amount_pyg")
	private Double amountPyg;
	@Column(name ="amount_eth")
	private Double amountEth;
	@Column(name ="transfer_to")
	private String transferToAddress;
	private Date date;
	@Column(name ="reason")
	private String reason;
}
