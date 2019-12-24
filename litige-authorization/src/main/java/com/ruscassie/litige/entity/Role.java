package com.ruscassie.litige.entity;

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

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@EqualsAndHashCode
public class Role {

	/**
	 *
	 */
	private static final long serialVersionUID = 8624315723258966098L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;

	@Column
	private String name;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "permission_role", joinColumns = {
			@JoinColumn(name = "role_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "permission_id", referencedColumnName = "id") })
	private List<Permission> permissions;

}
