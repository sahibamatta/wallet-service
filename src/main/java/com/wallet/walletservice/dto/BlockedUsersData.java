package com.wallet.walletservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class BlockedUsersData {
	
	String userId;
	String profile;
	String ip;
	String time;
	
}
