package com.wallet.walletservice.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table(name ="user_details")
public class UserEntity implements Serializable {

	private static final long serialVersionUID = -3688623313428654811L;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	@Column(name="first_name")
	private String firstName;
	@Column(name="last_name")
	private String lastName;
	@Column(name="user_id")
	private String userId;
	@Temporal(TemporalType.DATE)
	private Date birthday;
	private String phone;
	private String profile;
	@Column(name="email_id")
	private String emailId;
	private String password;
	@Column(name="user_locked")
	private Boolean userLocked;

}
