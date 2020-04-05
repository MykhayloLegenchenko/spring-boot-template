package com.softreactor.template.domain.jpa.account;

import java.time.Instant;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;

import com.softreactor.template.domain.account.User;
import com.softreactor.template.domain.jpa.BaseJpaEntity;

/**
 * User entity class for JPA.
 */
@Entity(name = "User")
@Table(indexes = {
	@Index(columnList="created")
})
public class UserEntity extends BaseJpaEntity implements User {
	@Column(nullable = false, columnDefinition = "timestamp")
	@CreationTimestamp
	private Instant created;
	
	@Column(nullable = false)
	private boolean enabled; 
	
	@Column(unique = true, nullable = false)
	private String email;

	@Column(length = 140, nullable = false)
	private String password;

	@ManyToMany
	@JoinTable(name = "user_role",
		joinColumns = @JoinColumn(name = "user_id", nullable = false),
		inverseJoinColumns = @JoinColumn(name = "role_id", nullable = false)
	)
	private Set<RoleEntity> roles;

	@Override
	public Instant getCreated() {
		return created;
	}

	@Override
	public void setCreated(Instant created) {
		this.created = created;
	}

	@Override
	public boolean IsEnabled() {
		return false;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public String getEmail() {
		return email;
	}

	@Override
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (!super.equals(obj))
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserEntity other = (UserEntity) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		return true;
	}

}
