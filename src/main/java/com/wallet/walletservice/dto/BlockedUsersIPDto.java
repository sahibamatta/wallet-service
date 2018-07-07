package com.wallet.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class BlockedUsersIPDto {

	private int status;
	private String message;
	private String ip;

}
