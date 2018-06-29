package com.wallet.walletservice.service;

import java.security.SecureRandom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.walletservice.dto.LoginRequestDto;
import com.wallet.walletservice.dto.LoginResponseDto;
import com.wallet.walletservice.entity.UserEntity;
import com.wallet.walletservice.repository.UserRepository;

@Service
public class LoginLogoutService {

	private static final String LOGIN_SUCCESS_MESSAGE="User logged in successfully"; 
	private static final String LOGIN_ERROR_MESSAGE="Invalid Username or Password";
	private static final String USER_LOCKED_ERROR = "User Locked";
	private static final int LOGIN_SUCCESS_STATUS=1;
	private static final int LOGIN_ERROR_STATUS=0;
	protected static SecureRandom random = new SecureRandom();

	@Autowired
	UserRepository userRepository;

	public LoginResponseDto loginUser(LoginRequestDto loginRequestDto) {
		System.out.println("in loginlogoutservice");
		UserEntity userEntity=userRepository.findByUserIdAndPassword(loginRequestDto.getUser(),loginRequestDto.getPassword());
		LoginResponseDto loginResponseDto = new LoginResponseDto(); 
		if(userEntity==null) {
			loginResponseDto.setStatus(LOGIN_ERROR_STATUS);
			loginResponseDto.setMessage(LOGIN_ERROR_MESSAGE);
		}

		else {
			if(userEntity.getUserLocked()) {
				loginResponseDto.setStatus(LOGIN_ERROR_STATUS);
				loginResponseDto.setMessage(USER_LOCKED_ERROR);
			}
			else {
				loginResponseDto.setStatus(LOGIN_SUCCESS_STATUS);
				loginResponseDto.setMessage(LOGIN_SUCCESS_MESSAGE);
				loginResponseDto.setToken(generateToken(loginRequestDto.getUser()));
				loginResponseDto.setProfile(userEntity.getProfile());
			}
		}
		return loginResponseDto;

	}

	public static synchronized String generateToken( String username ) {
		long longToken = Math.abs( random.nextLong() );
		String random = Long.toString( longToken, 16 );
		return ( username + ":" + random );
	}



}
