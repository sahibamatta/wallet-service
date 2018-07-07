package com.wallet.walletservice.service;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wallet.walletservice.dto.CurrencyParseResponseDto;
import com.wallet.walletservice.dto.CurrencyParserRequestDto;
import com.wallet.walletservice.dto.CurrencyParserResponseData;
import com.wallet.walletservice.entity.WalletCurrencyDetailsEntity;
import com.wallet.walletservice.repository.WalletCurrencyDetailsRepository;

@Service
public class CurrencyParserService {

	private static final String PYG_CURRENCY="PYG";
	private static final String ETH_CURRENCY="ETH";
	private static final String SUCCESS_MESSAGE="Currency conversion done successfully";
	private static final String ERROR_MESSAGE="No currency conversion found";
	private static final int SUCCESS_STATUS=1;
	private static final int ERROR_STATUS=0;
	private static final String FIRST_SOURCE="https://pyg.currencyrate.today/eth/";
	private static final String SECOND_SOURCE="https://freecurrencyrates.com/en/convert-PYG-ETH";
	private static final String THIRD_SOURCE="https://walletinvestor.com/converter/pyg/ethereum";

	@Autowired
	private WalletCurrencyDetailsRepository walletCurrencyDetailsRepository;

	public CurrencyParseResponseDto parseCurrency(CurrencyParserRequestDto currencyParserRequestDto) {

		System.out.println("in parsecurrency service currency is::"+currencyParserRequestDto.toString());

		CurrencyParseResponseDto currencyParseResponseDto = getFromTable(currencyParserRequestDto);

		try {
			if(currencyParseResponseDto==null) {
				currencyParseResponseDto = getFromDifferentSources(currencyParserRequestDto);
				insertIntoTable(currencyParserRequestDto,currencyParseResponseDto);
			}
			return currencyParseResponseDto;
		}

		catch(Exception e) {
			System.out.println("parseCurrency exception is::"+e);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+e, null);
		}
		return currencyParseResponseDto;
	}

	private CurrencyParseResponseDto getFromTable(CurrencyParserRequestDto currencyParserRequestDto) {
		return findInTable(currencyParserRequestDto);
	}

	private CurrencyParseResponseDto getFromDifferentSources(CurrencyParserRequestDto currencyParserRequestDto)
			throws IOException, ParseException {

		System.out.println("in getFromDifferentSources::");

		CurrencyParseResponseDto currencyParseResponseDto=getFromFirstSource(currencyParserRequestDto);
		if(currencyParseResponseDto!=null )
			return currencyParseResponseDto;

		currencyParseResponseDto=getFromSecondSource(currencyParserRequestDto);
		if(currencyParseResponseDto!=null)
			return currencyParseResponseDto;

		currencyParseResponseDto=getFromThirdSource(currencyParserRequestDto);
		if(currencyParseResponseDto!=null)
			return currencyParseResponseDto;

		currencyParseResponseDto=new CurrencyParseResponseDto();	
		currencyParseResponseDto.setStatus(ERROR_STATUS);
		currencyParseResponseDto.setMessage(ERROR_MESSAGE);
		System.out.println("in getFromDifferentSources end::");
		return currencyParseResponseDto;
	}

	private CurrencyParseResponseDto getFromFirstSource(CurrencyParserRequestDto currencyParserRequestDto) {

		System.out.println("in getfromsource1");
		try {
			String convertedCurrency="";
			Document doc = Jsoup.connect(FIRST_SOURCE).get();
			Elements currencyElements=doc.getElementsByClass("cc-result");
			System.out.println("currencyElements is:"+currencyElements);

			if(!currencyElements.isEmpty()) {
				for(int i=0;i<currencyElements.size();i++) {
					if(currencyElements.get(i).text().contains(PYG_CURRENCY) && currencyElements.get(i+1).text().contains(ETH_CURRENCY)) {
						System.out.println("pyg-eth conversion found");
						convertedCurrency=currencyElements.get(i+1).text().replace(ETH_CURRENCY,"").trim();
						break;
					}
				}
				return convertedCurrency.equals("")?null:new CurrencyParseResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE,
						new CurrencyParserResponseData(Double.parseDouble(convertedCurrency)*Double.parseDouble(currencyParserRequestDto.getCurrency1())));
			}
		}

		catch(NumberFormatException nfe) {
			System.out.println("getFromFirstSource number format exception--"+nfe);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+nfe, null);
		}
		catch(IOException io) {
			System.out.println("getFromFirstSource ioexception is::"+io);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+io, null);
		}
		catch(Exception e) {
			System.out.println("getFromFirstSource exception is::"+e);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+e, null);
		}
		return null;
	}

	private CurrencyParseResponseDto getFromSecondSource(CurrencyParserRequestDto currencyParserRequestDto) throws IOException{
		System.out.println("in getFromSecondSource");
		try {
			Document doc = Jsoup.connect(SECOND_SOURCE).get();
			Element pygElement=doc.getElementById("value_from");
			Element ethElement=doc.getElementById("value_to");
			System.out.println("pygElement is::"+pygElement+"::ethElemnt is::"+ethElement);

			return ((pygElement==null || ethElement==null)?null:new CurrencyParseResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE,
					new CurrencyParserResponseData(Double.parseDouble(ethElement.val())/Double.parseDouble(pygElement.val())
							*Double.parseDouble(currencyParserRequestDto.getCurrency1()))));
		}
		catch(NumberFormatException nfe) {
			System.out.println("getFromSecondSource number format exception--"+nfe);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+nfe, null);
		}
		catch(IOException io) {
			System.out.println("getFromSecondSource ioexception is::"+io);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+io, null);
		}
		catch(Exception e) {
			System.out.println("getFromSecondSource exception is::"+e);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+e, null);
		}

		return null;

	}

	private CurrencyParseResponseDto getFromThirdSource(CurrencyParserRequestDto currencyParserRequestDto) throws IOException {
		System.out.println("in getfromthirdsource");
		try {
			Document doc = Jsoup.connect(THIRD_SOURCE).get();
			Elements ethElements=doc.getElementsByClass("converter-title-amount");
			System.out.println("ethElements is::"+ethElements);
			String ethString=ethElements.get(0).text();
			return ethString.isEmpty()?null:new CurrencyParseResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE, 
					new CurrencyParserResponseData(Double.parseDouble(ethString)*Double.parseDouble(currencyParserRequestDto.getCurrency1())));
		}
		catch(NumberFormatException nfe) {
			System.out.println("getFromThirdSource number format exception--"+nfe);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+nfe, null);
		}
		catch(IOException io) {
			System.out.println("getFromThirdSource ioexception is::"+io);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+io, null);
		}
		catch(Exception e) {
			System.out.println("getFromThirdSource exception is::"+e);
			new CurrencyParseResponseDto(ERROR_STATUS, ERROR_MESSAGE+" due to::"+e, null);
		}
		return null;
	}

	private CurrencyParseResponseDto findInTable(CurrencyParserRequestDto currencyParserRequestDto) {
		System.out.println("in findInTable--");

		try {
			WalletCurrencyDetailsEntity walletCurrencyDetailsEntity=walletCurrencyDetailsRepository.findByDate(new Date());
			System.out.println("walletcurrencydetailsentity is::"+walletCurrencyDetailsEntity);

			if(walletCurrencyDetailsEntity==null) {
				return null;
			}
			else {
				Double pyg = Double.parseDouble(currencyParserRequestDto.getCurrency1());
				Double convertedEth=walletCurrencyDetailsEntity.getEth()*(pyg);
				return new CurrencyParseResponseDto(SUCCESS_STATUS,SUCCESS_MESSAGE,
						new CurrencyParserResponseData(convertedEth));
			}


		}
		catch(Exception e) {
			System.out.println("find in table exception");
		}
		return null;
	}

	private void insertIntoTable(CurrencyParserRequestDto currencyParserRequestDto,CurrencyParseResponseDto currencyParserResponseDto ) {
		System.out.println("in insertIntoTable");
		try {
			WalletCurrencyDetailsEntity walletCurrencyDetailsEntity = new WalletCurrencyDetailsEntity();
			walletCurrencyDetailsEntity.setDate(new Date());
			walletCurrencyDetailsEntity.setEth(currencyParserResponseDto.getData().getCurrency2()/
					Double.parseDouble(currencyParserRequestDto.getCurrency1()));
			walletCurrencyDetailsEntity.setPyg(1);
			walletCurrencyDetailsRepository.save(walletCurrencyDetailsEntity);
		}
		catch(Exception e) {
			System.out.println("in insertintotable exception--"+e);
		}

	}
}
