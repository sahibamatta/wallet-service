package com.wallet.walletservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlockedUsersData {
	
	private String userId;
	private String profile;
	private String ip;
	private String time;
	
}
