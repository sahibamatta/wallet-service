package com.wallet.walletservice.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDetailsDto {

	private String userId;
	private String profile;
	private Boolean isUserLocked;

}
