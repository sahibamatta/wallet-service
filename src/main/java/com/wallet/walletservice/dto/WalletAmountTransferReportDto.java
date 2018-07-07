package com.wallet.walletservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class WalletAmountTransferReportDto {
	
	private int status;
	private String message;
	private List<WalletTransferAmountRequestDto> walletTransferAmountRequestDto; 

}
