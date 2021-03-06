package com.wallet.walletservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlockedUsersReportDto {

	private int status;
	private String message;
	private List<BlockedUsersData> data; 
}
