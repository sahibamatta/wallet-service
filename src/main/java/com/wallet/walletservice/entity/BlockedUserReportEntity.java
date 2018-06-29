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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.wallet.configuration.LocalTimeConverter;

import lombok.Builder;
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
	int id;
	Date date;
	@Convert(converter = LocalTimeConverter.class)
	LocalTime time;
	@Column(name="user_id")
	String userId;
	@Column(name="user_profile")
	String userProfile;
	String ip;

}
