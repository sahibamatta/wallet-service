package com.wallet.walletservice.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.walletservice.dto.BlockedUsersData;
import com.wallet.walletservice.dto.BlockedUsersReportDto;
import com.wallet.walletservice.dto.CommonResponseDto;
import com.wallet.walletservice.dto.LoginRequestDto;
import com.wallet.walletservice.dto.LoginResponseDto;
import com.wallet.walletservice.dto.UserCreateRequestDto;
import com.wallet.walletservice.dto.UserDetailsDto;
import com.wallet.walletservice.dto.UserNameDto;
import com.wallet.walletservice.dto.UserProfileDto;
import com.wallet.walletservice.dto.UserUpdateRequestDto;
import com.wallet.walletservice.service.BlockedUserService;
import com.wallet.walletservice.service.LoginLogoutService;
import com.wallet.walletservice.service.UserService;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {

	@Autowired
	UserService userService;

	@Autowired
	LoginLogoutService loginLogoutService;
	
	@Autowired
	BlockedUserService blockedUserService;

	@RequestMapping(value="/test" , method = RequestMethod.GET)
	public String test() {
		System.out.println("in test");
		return "test success";
	}

	@RequestMapping(value="/create" , method = RequestMethod.POST)
	public CommonResponseDto createUser(@RequestBody UserCreateRequestDto userCreateRequestDto) {
		System.out.println("in createUser userCreateDto is::"+userCreateRequestDto.toString());
		return userService.createUser(userCreateRequestDto);

	}

	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public UserProfileDto getUserProfiles() {
		System.out.println("in getUserProfiles");
		return userService.getUserProfiles();

	}

	@RequestMapping(value="/id", method=RequestMethod.GET)
	public UserNameDto getUserIds() {
		System.out.println("in getUserId");
		return userService.getUserIds();
	}

	@RequestMapping(value="/details/{id}", method=RequestMethod.GET)
	public UserDetailsDto getUserDetailsById(@PathVariable("id") String userId ) {
		System.out.println("in getUserDetailsById id is::"+userId);
		return userService.getUserDetailsByUserId(userId);
	}

	@RequestMapping(value="/update" , method = RequestMethod.PUT)
	public CommonResponseDto updateUser(@RequestBody UserUpdateRequestDto userUpdateRequestDto) {
		System.out.println("in updateUser UserUpdateRequestDto is::"+userUpdateRequestDto.toString());
		return userService.updateUser(userUpdateRequestDto);

	}

	@RequestMapping(value="/delete/{id}" , method = RequestMethod.DELETE)
	public CommonResponseDto deleteUser(@PathVariable("id") String userId) {
		System.out.println("in delete userID is::"+userId);
		return userService.deleteUser(userId);

	}

	@RequestMapping(value="/login" , method = RequestMethod.POST)
	public LoginResponseDto loginUser(@RequestBody LoginRequestDto loginRequestDto) {
		System.out.println("in loginUser loginRequestDto is::"+loginRequestDto.toString());
		return loginLogoutService.loginUser(loginRequestDto);

	}

	@RequestMapping(value="/blocked/{strDate}" , method = RequestMethod.GET)
	public BlockedUsersReportDto blockedUsersReport(@PathVariable("strDate") String strDate) {
		System.out.println("in blockedUsersReport date is::"+strDate);
		return blockedUserService.getBlockedUsersReport(strDate);
	}
	

	/*@RequestMapping(value="/profile", method=RequestMethod.GET)
	public List<UserProfileDto> checkIfUserIdAlreadyExists(@RequestBody UserCheckRequestDto userCheckRequestDto) {
		System.out.println("in checkIfUserIdAlreadyExist");
		return userService.checkIfUserIdAlreadyExists();

	}*/

}
