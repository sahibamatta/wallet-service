package com.wallet.walletservice.dto;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlockedUsersReportDto {

	int status;
	String message;
	List<BlockedUsersData> data; 
}
