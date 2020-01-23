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
 * The persistent class for the dossier database table.
 *
 */
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Dossier implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = 2264379545320971834L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;

	private ZonedDateTime creation;

	private String resolution;

	// bi-directional many-to-one association to Comment
	@OneToMany(mappedBy = "dossier")
	private List<Comment> comments;

	// bi-directional many-to-one association to Claim
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "claim_id")
	private Claim claim;

	// bi-directional many-to-one association to User
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agent_id")
	private User user;

	// bi-directional many-to-one association to Report
	@OneToMany(mappedBy = "dossier")
	private List<Report> reports;

	// bi-directional many-to-many association to Session
	@ManyToMany
	@JoinTable(name = "session_dossier", joinColumns = { @JoinColumn(name = "dossier_id") }, inverseJoinColumns = {
			@JoinColumn(name = "session_id") })
	private List<Session> sessions;

}