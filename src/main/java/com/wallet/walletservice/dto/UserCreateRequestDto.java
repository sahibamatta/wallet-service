package com.wallet.walletservice.dto;

import java.util.Date;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserCreateRequestDto {
	
	private String firstName;
	private String lastName;
	private String userId;
	private Date birthday;
	private String phone;
	private String profile;
	private String emailId;
	private String password;
	private String confirmPassword;
	
}
