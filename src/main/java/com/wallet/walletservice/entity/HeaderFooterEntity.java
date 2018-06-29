package com.wallet.walletservice.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@Entity
@Table(name = "header_footer")
public class HeaderFooterEntity implements Serializable {

	private static final long serialVersionUID = 4889605383366715692L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private String header;
	private String footer; 

}
