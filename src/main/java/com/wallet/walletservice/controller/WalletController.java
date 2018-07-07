package com.wallet.walletservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.walletservice.dto.CommonResponseDto;
import com.wallet.walletservice.dto.CurrencyParseResponseDto;
import com.wallet.walletservice.dto.CurrencyParserRequestDto;
import com.wallet.walletservice.dto.TncResponseDto;
import com.wallet.walletservice.dto.WalletAddressesResposeDto;
import com.wallet.walletservice.dto.WalletAmountTransferReportDto;
import com.wallet.walletservice.dto.WalletTransferAmountRequestDto;
import com.wallet.walletservice.service.CurrencyParserService;
import com.wallet.walletservice.service.WalletService;

@RestController
@RequestMapping("wallet")
@CrossOrigin
public class WalletController {

	@Autowired
	CurrencyParserService currencyParserService;

	@Autowired
	WalletService walletService;

	@RequestMapping(value="/currency" , method=RequestMethod.POST)
	public CurrencyParseResponseDto parseCurrency(@RequestBody CurrencyParserRequestDto currencyParserRequestDto) {
		System.out.println("in parseCurrencyController");
		//currencyParserRequestDto.setCurrency1("10");
		return currencyParserService.parseCurrency(currencyParserRequestDto); 
	}

	@RequestMapping(value="/address" , method=RequestMethod.GET)
	public WalletAddressesResposeDto getWalletAddresses() {
		System.out.println("in getWalletAddresses");
		return walletService.getWalletAddresses();
	}

	@RequestMapping(value="/tnc" , method=RequestMethod.GET)
	public TncResponseDto getTnc() {
		System.out.println("in getTnc");
		return walletService.getTnc();
	}

	@RequestMapping(value="/transfer" , method=RequestMethod.POST)
	public CommonResponseDto transferAmount(@RequestBody WalletTransferAmountRequestDto walletTransferAmountDto) {
		System.out.println("in transferAmount");
		return walletService.transferAmount(walletTransferAmountDto);
	}

	@RequestMapping(value="/report/{start}/{end}" , method=RequestMethod.GET)
	public WalletAmountTransferReportDto amountTransferReport(@PathVariable("start") String start, @PathVariable("end") String end) {
		System.out.println("in transferAmount");
		return walletService.amountTransferReport(start, end);
	}

}
