package com.softreactor.template.domain.jpa.account;

import static javax.persistence.CascadeType.REMOVE;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

import com.softreactor.template.domain.account.Role;
import com.softreactor.template.domain.jpa.BaseJpaEntity;

/**
 * Role entity class for JPA.
 */
@Entity(name = "Role")
public class RoleEntity extends BaseJpaEntity implements Role {

	@Column(unique = true, nullable = false)
	private String name;

	@ManyToMany(mappedBy = "roles", cascade = REMOVE)
	private Set<UserEntity> users;

	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((users == null) ? 0 : users.hashCode());
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
		RoleEntity other = (RoleEntity) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (users == null) {
			if (other.users != null)
				return false;
		} else if (!users.equals(other.users))
			return false;
		return true;
	}
}
