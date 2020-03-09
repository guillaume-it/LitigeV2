package com.ruscassie.litige.entity;

import java.io.Serializable;
import java.time.ZonedDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The persistent class for the comment database table.
 *
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Comment implements Serializable {
	/**
	 *
	 */
	private static final long serialVersionUID = 6698956408923587235L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;

	private ZonedDateTime creation;

	private String message;

	private ZonedDateTime update;

	// bi-directional many-to-one association to User
//	@ManyToOne(fetch = FetchType.LAZY)
//	@JoinColumn(name = "agent_id")
//	private User agent;
	@Column
	private Long agentId;

	// bi-directional many-to-one association to Dossier
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dossier_id")
	private Dossier dossier;

}