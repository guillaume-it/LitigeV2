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

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the session database table.
 *
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Session implements Serializable {

	/**
	 *
	 */
	private static final long serialVersionUID = -4771733870051365552L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;

	private ZonedDateTime creation;

	private String message;

	private String objet;

	// bi-directional many-to-one association to User
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "reporter_id")
//	private User reporter;
	@Column
	private Long reporterId;

	// bi-directional many-to-one association to User
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "president_id")
//	private User president;
	@Column
	private long presidentId;

	// bi-directional many-to-many association to Dossier
	@ManyToMany
	@JoinTable(name = "session_dossier", joinColumns = { @JoinColumn(name = "session_id") }, inverseJoinColumns = {
			@JoinColumn(name = "dossier_id") })
	private List<Dossier> dossiers;

}