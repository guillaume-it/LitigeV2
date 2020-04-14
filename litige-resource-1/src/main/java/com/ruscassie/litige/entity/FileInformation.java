package com.ruscassie.litige.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class FileInformation implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -8700860387021132090L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;
	@Column
	private String name;
	@Column
	private String uri;
	@Column
	private String contentType;
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long size;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "claim_file_information", joinColumns = {
			@JoinColumn(name = "file_information_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "claim_id", referencedColumnName = "id") })
	private List<Claim> claims;
}