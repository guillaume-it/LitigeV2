package com.ruscassie.litige.entity;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@Entity(name = "userdb")
@NoArgsConstructor
@EqualsAndHashCode
public class User implements UserDetails {

	/**
	 *
	 */
	private static final long serialVersionUID = 1902208461396516870L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(columnDefinition = "NUMERIC(19,0)")
	private Long id;

	@Column
	private String email;

	@Column
	private String username;

	@Column
	private String password;

	@Column
	private boolean enabled;

	@Column(name = "account_locked")
	private boolean accountNonLocked;

	@Column(name = "account_expired")
	private boolean accountNonExpired;

	@Column(name = "credentials_expired")
	private boolean credentialsNonExpired;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "role_user", joinColumns = {
			@JoinColumn(name = "user_id", referencedColumnName = "id") }, inverseJoinColumns = {
					@JoinColumn(name = "role_id", referencedColumnName = "id") })
	private List<Role> roles;

	/*
	 * Get roles and permissions and add them as a Set of GrantedAuthority
	 */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		final Set<GrantedAuthority> authorities = new HashSet<GrantedAuthority>();

		roles.forEach(r -> {
			authorities.add(new SimpleGrantedAuthority(r.getName()));
			r.getPermissions().forEach(p -> {
				authorities.add(new SimpleGrantedAuthority(p.getName()));
			});
		});

		return authorities;
	}

	@Override
	public boolean isAccountNonExpired() {
		return !accountNonExpired;
	}

	@Override
	public boolean isAccountNonLocked() {
		return !accountNonLocked;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !credentialsNonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
}
