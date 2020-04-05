package com.softreactor.template.domain.jpa;

import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;

import com.softreactor.template.domain.DomainUtils;

/**
 * Base JPA entity class.
 */
@MappedSuperclass
public class BaseJpaEntity {

	@Id
	@Column(columnDefinition = "binary(16)")
	private UUID id;

	/**
	 * Generates id for new entity.
	 */
	@PrePersist
	private void generateId() {
		if (id == null) {
			id = DomainUtils.generateId();
		}
	}

	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BaseJpaEntity other = (BaseJpaEntity) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
