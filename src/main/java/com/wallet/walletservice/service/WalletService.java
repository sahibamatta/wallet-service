package com.wallet.walletservice.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.walletservice.dto.CommonResponseDto;
import com.wallet.walletservice.dto.ReasonDto;
import com.wallet.walletservice.dto.TncResponseDto;
import com.wallet.walletservice.dto.TransferFromDto;
import com.wallet.walletservice.dto.WalletAddressesResposeDto;
import com.wallet.walletservice.dto.WalletAmountTransferReportDto;
import com.wallet.walletservice.dto.WalletTransferAmountRequestDto;
import com.wallet.walletservice.entity.WalletTermsAndConditions;
import com.wallet.walletservice.entity.WalletTransferReportEntity;
import com.wallet.walletservice.properties.WalletProperties;
import com.wallet.walletservice.repository.WalletProfilesRepository;
import com.wallet.walletservice.repository.WalletReasonRepository;
import com.wallet.walletservice.repository.WalletTermsAndConditionsRepository;
import com.wallet.walletservice.repository.WalletTransferReprotRepository;

@Service
public class WalletService {

	private static final String SUCCESS_MESSAGE ="Amount Transferred Successfully";
	private static final String ERROR_MESSAGE = "Amount couldn't be transferred due to ";
	private static final String REPORT_SUCCESS_MESSAGE ="Data fetched successfully";
	private static final String REPORT_ERROR_MESSAGE = "No data found for the given dates";
	private static final String REPORT_ERROR_MESSAGE2 = "Data couldn't be fetched due to:";
	private static final int SUCCESS_STATUS = 1;
	private static final int ERROR_STATUS = 0;

	@Autowired
	private WalletProfilesRepository walletProfilesRepository;

	@Autowired
	private WalletTermsAndConditionsRepository walletTermsAndConditionsRepository;

	@Autowired
	private WalletTransferReprotRepository walletTransferReprotRepository;

	@Autowired
	private WalletProperties walletProperties;

	@Autowired
	private WalletReasonRepository walletReasonRepository;

	public WalletAddressesResposeDto getWalletAddresses() {
		System.out.println("in getWalletAddreesses service");
		return new WalletAddressesResposeDto(walletProfilesRepository.getWalletAddresses());

	}

	public TncResponseDto getTnc() {
		System.out.println("in getTnc");
		WalletTermsAndConditions walletTermsAndConditions = walletTermsAndConditionsRepository.findOne(1); 
		return new TncResponseDto(walletTermsAndConditions.getTitle(),walletTermsAndConditions.getBody());

	}

	public CommonResponseDto transferAmount(WalletTransferAmountRequestDto walletTransferAmountRequestDto) {
		System.out.println("in transferAmount service");
		WalletTransferReportEntity walletTransferReportEntity = new WalletTransferReportEntity();
		try {
			walletTransferReportEntity.setTransferFromAddress(walletTransferAmountRequestDto.getTransferFrom());
			walletTransferReportEntity.setTransferToAddress(walletTransferAmountRequestDto.getTransferTo());
			walletTransferReportEntity.setAmountPyg(Double.parseDouble(walletTransferAmountRequestDto.getAmountPyg()));
			walletTransferReportEntity.setAmountEth(Double.parseDouble(walletTransferAmountRequestDto.getAmountEth()));
			walletTransferReportEntity.setDate(new Date());
			walletTransferReportEntity.setReason(walletTransferAmountRequestDto.getReason());
			walletTransferReprotRepository.save(walletTransferReportEntity);
			return new CommonResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE);
		}

		catch(Exception e) {
			System.out.println("exception in transferAmount::"+e);
			return new CommonResponseDto(ERROR_STATUS,ERROR_MESSAGE+e);
		}
	}

	public WalletAmountTransferReportDto amountTransferReport(String start , String end) {
		System.out.println("in amountTransferReport start is::"+start+"::end is::"+end);
		List <WalletTransferAmountRequestDto> walletTransferAmountRequestDtoList = new ArrayList<>();
		try {
			Date startDate = new SimpleDateFormat("yyyy-MM-dd").parse(start);
			Date endDate = new SimpleDateFormat("yyyy-MM-dd").parse(end);
			List <WalletTransferReportEntity> walletTransferReportEntityList=
					walletTransferReprotRepository.getDateBetween(startDate, endDate);
			System.out.println("walletTransferReportEntityList is::"+walletTransferReportEntityList);
			if(walletTransferReportEntityList==null)
				return new WalletAmountTransferReportDto(ERROR_STATUS, REPORT_ERROR_MESSAGE, null);

			for(WalletTransferReportEntity walletTransferReportEntity:walletTransferReportEntityList) {
				WalletTransferAmountRequestDto walletTransferAmountRequestDto = new WalletTransferAmountRequestDto
						(walletTransferReportEntity.getTransferFromAddress(), walletTransferReportEntity.getAmountPyg().toString(), 
								walletTransferReportEntity.getAmountEth().toString(),walletTransferReportEntity.getTransferToAddress(),
								new SimpleDateFormat("yyyy-MM-dd").format(walletTransferReportEntity.getDate()),
								walletTransferReportEntity.getReason()); 
				walletTransferAmountRequestDtoList.add(walletTransferAmountRequestDto);
			}
			return new WalletAmountTransferReportDto(SUCCESS_STATUS, REPORT_SUCCESS_MESSAGE, walletTransferAmountRequestDtoList);
		}
		catch(Exception e) {
			System.out.println("exception in amountTransferReport::"+e);
			return new WalletAmountTransferReportDto(ERROR_STATUS, REPORT_ERROR_MESSAGE2+e, walletTransferAmountRequestDtoList);
		}
	}

	public TransferFromDto getTransferFromWalletAddress() {
		System.out.println("in getTransferFromWalletAddesss walletaddress is::"+walletProperties.getWalletAddress());
		return new TransferFromDto(walletProperties.getWalletAddress());

	}

	public ReasonDto getReasonForTransfer() {
		System.out.println("in getReasonForTransfer service");
		return new ReasonDto(walletReasonRepository.getReasons());
	}
}
