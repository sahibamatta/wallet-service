package com.wallet.walletservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	
	private int status;
	private String message;
	private String token;
	private String profile;

}
