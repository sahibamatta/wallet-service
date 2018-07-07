package com.wallet.walletservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CurrencyParseResponseDto {

	private int status;
	private String message;
	private CurrencyParserResponseData data;

}
