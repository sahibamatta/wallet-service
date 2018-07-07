package com.wallet.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WalletTransferAmountRequestDto {
	
	private String transferFrom;
	private String amountPyg;
	private String amountEth;
	private String transferTo;
	private String date;

}
