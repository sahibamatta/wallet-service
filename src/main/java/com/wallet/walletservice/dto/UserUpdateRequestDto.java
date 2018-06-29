package com.wallet.walletservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class UserUpdateRequestDto {

	private String userId;
	private String profile;
	private Boolean isUserLocked;
	private Boolean changePassword;
	private String oldPassword;
	private String newPassword;

}
