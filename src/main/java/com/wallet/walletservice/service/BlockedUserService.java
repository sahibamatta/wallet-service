package com.wallet.walletservice.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.walletservice.dto.BlockedUsersData;
import com.wallet.walletservice.dto.BlockedUsersIPDto;
import com.wallet.walletservice.dto.BlockedUsersReportDto;
import com.wallet.walletservice.entity.BlockedUserReportEntity;
import com.wallet.walletservice.repository.BlockedUserReportRepository;

@Service
public class BlockedUserService {

	private static final String BLOCKED_USER_SUCCESS_MESSAGE ="List Of blocked users fetched succesfully";
	private static final String BLOCKED_USER_ERROR_MESSAGE = "No users blocked for the selected date";
	private static final int SUCCESS_STATUS = 1;
	private static final int ERROR_STATUS = 0;

	@Autowired
	BlockedUserReportRepository blockedUserReportRepository;

	public BlockedUsersIPDto getBlockedUserIP() {
		System.out.println("in getBlockedUsersIp");
		String output="";
		try {

			URL url = new URL("https://www.l2.io/ip");
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ conn.getResponseCode());
			}

			BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));


			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
			}

			conn.disconnect();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();

		}
		return new BlockedUsersIPDto(1,"IP successfully fetched",output);
	}

	public BlockedUsersReportDto getBlockedUsersReport(String requestDateStr){

		System.out.println("in getBlockedUsersReportservice--"+requestDateStr);

		Date requestDate=null;
		try {
			requestDate = new SimpleDateFormat("yyyy-MM-dd").parse(requestDateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}  
		List<BlockedUserReportEntity> blockedUserReportEntityList =blockedUserReportRepository.findByDate(requestDate);
		List <BlockedUsersData> blockedUsersDataList = new ArrayList<>();
		if(blockedUserReportEntityList!=null) {

			for(BlockedUserReportEntity blockedUserReportEntity :blockedUserReportEntityList) {
				BlockedUsersData blockedUsersData = new BlockedUsersData();	
				blockedUsersData.setUserId(blockedUserReportEntity.getUserId());
				blockedUsersData.setProfile(blockedUserReportEntity.getUserProfile());
				blockedUsersData.setIp(blockedUserReportEntity.getIp());
				blockedUsersData.setTime(blockedUserReportEntity.getTime()+"");
				blockedUsersDataList.add(blockedUsersData);
			}
			return new BlockedUsersReportDto(SUCCESS_STATUS,BLOCKED_USER_SUCCESS_MESSAGE, blockedUsersDataList);	
		}
		else {
			return new BlockedUsersReportDto(ERROR_STATUS,BLOCKED_USER_ERROR_MESSAGE, blockedUsersDataList);
		}

	}
	
}

