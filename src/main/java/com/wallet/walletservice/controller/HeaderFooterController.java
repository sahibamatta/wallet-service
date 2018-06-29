package com.wallet.walletservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.wallet.walletservice.dto.HeaderFooterDto;
import com.wallet.walletservice.service.HeaderFooterService;

@RestController
@RequestMapping("/header-footer")
@CrossOrigin
public class HeaderFooterController {
	
	@Autowired
	HeaderFooterService headerFooterService;
	
	@RequestMapping(value="/header" , method = RequestMethod.GET)
	public HeaderFooterDto getHeader() {
		System.out.println("in getHeader");
		return headerFooterService.getHeader();
	}
	
	@RequestMapping(value="/footer" , method = RequestMethod.GET)
	public HeaderFooterDto getFooter() {
		System.out.println("in getFooter");
		return headerFooterService.getFooter();
	}

}
