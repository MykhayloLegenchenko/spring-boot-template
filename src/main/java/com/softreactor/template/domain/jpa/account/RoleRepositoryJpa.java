package com.softreactor.template.domain.jpa.account;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.softreactor.template.domain.account.Role;
import com.softreactor.template.domain.account.RoleRepository;

/**
 * Role repository JPA interface.
 */
public interface RoleRepositoryJpa extends RoleRepository, JpaRepository<RoleEntity, UUID> {

	@Override
	default Role createObject() {
		return new RoleEntity();
	}

	@Override
	default void persist(Role entity) {
		saveAndFlush((RoleEntity) entity);
	}
}
