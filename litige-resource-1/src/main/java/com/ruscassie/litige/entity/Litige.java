package com.ruscassie.litige.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class Litige {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;
	@Column
	private String objet;
	@Column
	private String message;
	@Column
	private Date creation;

	@ManyToOne()
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private User requerant;
	@ManyToOne()
	@JoinColumn(name = "id", insertable = false, updatable = false)
	private User agent;

}
