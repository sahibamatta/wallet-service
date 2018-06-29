package com.wallet.walletservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.walletservice.dto.HeaderFooterDto;
import com.wallet.walletservice.repository.HeaderFooterRepository;

@Service
public class HeaderFooterService {
	
	@Autowired
	private HeaderFooterRepository headerFooterRepository;
	
	public HeaderFooterDto getHeader() {
		System.out.println("in getHeader service");
		HeaderFooterDto headerFooterDto = new HeaderFooterDto();
		headerFooterDto.setHeader(headerFooterRepository.findAll().iterator().next().getHeader());
		return headerFooterDto;
	}
	
	public HeaderFooterDto getFooter() {
		System.out.println("in getFooter service");
		HeaderFooterDto headerFooterDto = new HeaderFooterDto();
		headerFooterDto.setFooter(headerFooterRepository.findAll().iterator().next().getFooter());
		return headerFooterDto;
	}

}
