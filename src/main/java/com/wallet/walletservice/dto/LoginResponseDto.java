package com.wallet.walletservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDto {
	
	int status;
	String message;
	String token;
	String profile;

}
