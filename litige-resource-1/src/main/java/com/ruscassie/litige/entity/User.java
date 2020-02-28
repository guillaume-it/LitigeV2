//package com.ruscassie.litige.entity;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.time.ZonedDateTime;
//
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.Table;
//
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
///**
// * The persistent class for the users database table.
// *
// */
//@Setter
//@Getter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//@Table(name = "users")
//public class User implements Serializable {
//
//	/**
//	 *
//	 */
//	private static final long serialVersionUID = 8644258165086871470L;
//
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	@Column(columnDefinition = "NUMERIC(19,0)")
//	private Long id;
//
//	@Column(name = "account_expired")
//	private Boolean accountExpired;
//
//	@Column(name = "account_locked")
//	private Boolean accountLocked;
//
//	@Column(name = "created_on")
//	private ZonedDateTime createdOn;
//
//	@Column(name = "credentials_expired")
//	private Boolean credentialsExpired;
//
//	private String email;
//
//	private Boolean enabled;
//
//	@Column(name = "first_name")
//	private String firstName;
//
//	@Column(name = "last_name")
//	private String lastName;
//
//	private String password;
//
//	private String phone;
//
//	@Column(name = "updated_on")
//	private ZonedDateTime updatedOn;
//
//	private BigDecimal version;
//
//}