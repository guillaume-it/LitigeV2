package com.ruscassie.litige.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;
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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the claim database table.
 *
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Claim implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -1130749107152396936L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;
	@Column
	private ZonedDateTime creation;
	@Column
	private ZonedDateTime modification;
	@Column
	private String message;
	@Column
	private String objet;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id")
	private User agent;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "claimant_id")
	private User claimant;

	// bi-directional many-to-one association to Dossier
	@OneToMany(mappedBy = "claim")
	private List<Dossier> dossiers;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "claim_file_information", joinColumns = {
			@JoinColumn(name = "claim_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "file_information_id", referencedColumnName = "id") })
	private List<FileInformation> fileInformations;

}