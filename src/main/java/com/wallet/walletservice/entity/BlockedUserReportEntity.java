package com.wallet.walletservice.entity;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.wallet.configuration.LocalTimeConverter;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="blocked_user_report")
@NoArgsConstructor
public class BlockedUserReportEntity implements Serializable {


	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	private Date date;
	@Convert(converter = LocalTimeConverter.class)
	private LocalTime time;
	@Column(name="user_id")
	private String userId;
	@Column(name="user_profile")
	private String userProfile;
	private String ip;

}
