package com.wallet.walletservice.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.walletservice.dto.CommonResponseDto;
import com.wallet.walletservice.dto.UserCreateRequestDto;
import com.wallet.walletservice.dto.UserDetailsDto;
import com.wallet.walletservice.dto.UserNameDto;
import com.wallet.walletservice.dto.UserProfileDto;
import com.wallet.walletservice.dto.UserUpdateRequestDto;
import com.wallet.walletservice.entity.BlockedUserReportEntity;
import com.wallet.walletservice.entity.UserEntity;
import com.wallet.walletservice.entity.UserProfileEntity;
import com.wallet.walletservice.repository.BlockedUserReportRepository;
import com.wallet.walletservice.repository.UserProfileRepository;
import com.wallet.walletservice.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserProfileRepository userProfileRepository;

	@Autowired
	private BlockedUserReportRepository blockedUserReportRepository;

	private static final String SUCCESS_MESSAGE ="User Created Successfully";
	private static final String ERROR_MESSAGE = " already exists";
	private static final String UPDATE_SUCCESS_MESSAGE ="User Updated Successfully";
	private static final String UPDATE_ERROR_MESSAGE = "Error Updating User";
	private static final int SUCCESS_STATUS = 1;
	private static final int ERROR_STATUS = 0;
	private static final String DELETE_MESSAGE ="User deleted Successfully";


	public CommonResponseDto createUser(UserCreateRequestDto userCreateRequestDto) {
		System.out.println("in createUSer--");
		CommonResponseDto userCreateResponseDto = new CommonResponseDto();
		UserEntity userEntity=userRepository.findTop1ByEmailIdOrUserId(userCreateRequestDto.getEmailId(),userCreateRequestDto.getUserId());
		//System.out.println("userentity after find by email is::"+userEntity);

		if(userEntity==null) {
			System.out.println("in if create new user");
			userEntity = UserEntity.builder().firstName(userCreateRequestDto.getFirstName())
					.lastName(userCreateRequestDto.getLastName()).userId(userCreateRequestDto.getUserId())
					.birthday(userCreateRequestDto.getBirthday()).phone(userCreateRequestDto.getPhone())
					.profile(userCreateRequestDto.getProfile()).emailId(userCreateRequestDto.getEmailId())
					.password(userCreateRequestDto.getPassword()).userLocked(false).build();

			userRepository.save(userEntity);
			userCreateResponseDto.setStatus(SUCCESS_STATUS);
			userCreateResponseDto.setMessage(SUCCESS_MESSAGE);
		}

		else {
			System.out.println("in else existing user");
			userCreateResponseDto.setStatus(ERROR_STATUS);
			if(userCreateRequestDto.getEmailId().equals(userEntity.getEmailId())) {
				userCreateResponseDto.setMessage("This emailId "+ERROR_MESSAGE);
			}
			else {
				userCreateResponseDto.setMessage("This userId"+ERROR_MESSAGE);
			}
		}

		return userCreateResponseDto;

	}

	public UserProfileDto getUserProfiles() {
		System.out.println("in getUserProfilesService");
		List<UserProfileEntity> userProfileList = new ArrayList<>();
		List<String> userProfiles = new ArrayList<>();
		userProfileRepository.findAll().forEach(userProfileList::add);
		for(UserProfileEntity userProfile:userProfileList) {
			userProfiles.add(userProfile.getProfile());
		}
		UserProfileDto userProfileDto = new UserProfileDto(userProfiles);

		return userProfileDto	;

	}

	public UserNameDto getUserIds() {
		System.out.println("getUSerId service");
		UserNameDto userNameDto=new UserNameDto(userRepository.getUserIds());
		System.out.println("usernamedto is::"+userNameDto);
		return userNameDto;
	}

	public UserDetailsDto getUserDetailsByUserId(String userId) {
		System.out.println("getUserDetailsByUserId entered");
		UserEntity userEntity = userRepository.findByUserId(userId);
		UserDetailsDto userDetailsDto = new UserDetailsDto();
		//System.out.println("userEntity is "+userEntity);
		if(userEntity!=null) {
			System.out.println("in if userentity is not null");
			//System.out.println("userEntity to string is "+userEntity.toString());
			userDetailsDto.setProfile(userEntity.getProfile());
			userDetailsDto.setIsUserLocked(userEntity.getUserLocked()==null||userEntity.getUserLocked()==false?false:true);
		}
		return userDetailsDto;
	}

	public CommonResponseDto updateUser(UserUpdateRequestDto userUpdateRequestDto) {
		System.out.println("in updateUSer--");
		CommonResponseDto userCreateResponseDto = new CommonResponseDto();

		try {
			UserEntity userEntity=userRepository.findByUserId(userUpdateRequestDto.getUserId());
			//System.out.println("userentity after find by userid is::"+userEntity);

			if(userEntity==null) {
				System.out.println("in if update user doesnt exist");
				userCreateResponseDto.setStatus(ERROR_STATUS);
				userCreateResponseDto.setMessage(UPDATE_ERROR_MESSAGE);
				return userCreateResponseDto;

			}

			else {

				if(userUpdateRequestDto.getChangePassword()) {

					if(!userUpdateRequestDto.getOldPassword().equals(userEntity.getPassword())) {
						userCreateResponseDto.setStatus(ERROR_STATUS);
						userCreateResponseDto.setMessage(UPDATE_ERROR_MESSAGE+" due to incorrect old password");
						return userCreateResponseDto;
					}
					userEntity.setPassword(userUpdateRequestDto.getNewPassword());
				}

				CommonResponseDto commonResponseDto =insertUpdateOrDeleteBlockedUsers(userUpdateRequestDto);

				userEntity.setProfile(userUpdateRequestDto.getProfile());
				userEntity.setUserLocked(userUpdateRequestDto.getIsUserLocked());

				if(commonResponseDto.getStatus()==0) {
					userCreateResponseDto.setStatus(ERROR_STATUS);
					userCreateResponseDto.setMessage(commonResponseDto.getMessage());
					return userCreateResponseDto;
				}

				userCreateResponseDto.setStatus(SUCCESS_STATUS);
				userCreateResponseDto.setMessage(UPDATE_SUCCESS_MESSAGE);
				userRepository.save(userEntity);
			}

		}
		catch(Exception e) {
			System.out.println("update user exception--"+e);
			userCreateResponseDto.setStatus(ERROR_STATUS);
			userCreateResponseDto.setMessage(UPDATE_ERROR_MESSAGE+" due to "+e);
		}

		return userCreateResponseDto;
	}

	public CommonResponseDto deleteUser(String userId) {
		System.out.println("deleteUser service userID is::"+userId);
		userRepository.deleteUsersByUserId(userId);
		CommonResponseDto userCreateResponseDto = new CommonResponseDto();
		userCreateResponseDto.setStatus(SUCCESS_STATUS);
		userCreateResponseDto.setMessage(DELETE_MESSAGE);

		return userCreateResponseDto;
	}

	public CommonResponseDto insertUpdateOrDeleteBlockedUsers(UserUpdateRequestDto userUpdateRequestDto) {
		System.out.println("in insertUpdateOrDeleteBlockedUsers");

		BlockedUserReportEntity blockedUserReportEntity=blockedUserReportRepository.findByUserId(userUpdateRequestDto.getUserId());

		try {
			if(blockedUserReportEntity==null && userUpdateRequestDto.getIsUserLocked()) {
				blockedUserReportEntity = new BlockedUserReportEntity();
				blockedUserReportEntity.setDate(new Date());
				blockedUserReportEntity.setIp("localhost");
				blockedUserReportEntity.setTime(java.time.LocalTime.now());
				blockedUserReportEntity.setUserId(userUpdateRequestDto.getUserId());
				blockedUserReportEntity.setUserProfile(userUpdateRequestDto.getProfile());
				blockedUserReportRepository.save(blockedUserReportEntity);	
				return new CommonResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE);
			}

			else if(blockedUserReportEntity!=null) {
				if(userUpdateRequestDto.getIsUserLocked()) {
					blockedUserReportEntity.setUserProfile(userUpdateRequestDto.getProfile());
					blockedUserReportRepository.save(blockedUserReportEntity);
					return new CommonResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE);
				}
				else {
					blockedUserReportRepository.delete(blockedUserReportEntity);
					return new CommonResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE);
				}
			}
		}
		catch(Exception e) {
			return new CommonResponseDto(ERROR_STATUS, ERROR_MESSAGE+e);
		}
		return new CommonResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE);
	}

}
