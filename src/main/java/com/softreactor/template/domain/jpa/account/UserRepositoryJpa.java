package com.softreactor.template.domain.jpa.account;

import java.util.List;
import java.util.UUID;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.softreactor.template.domain.SearchFilter;
import com.softreactor.template.domain.SearchResult;
import com.softreactor.template.domain.account.User;
import com.softreactor.template.domain.account.UserRepository;
import com.softreactor.template.domain.jpa.JpaUtils;

/**
 * User repository JPA interface.
 */
public interface UserRepositoryJpa extends UserRepository, JpaRepository<UserEntity, UUID> {

	@Override
	default User createObject() {
		return new UserEntity();
	}

	@Override
	default void persist(User entity) {
		saveAndFlush((UserEntity) entity);
	}
	
	@Override
	@Query(Queries.FIND_USER_ROLES)
	List<String> findRoles(UUID userId); 

	@Override
	@Transactional
	@Modifying
	@Query(value = Queries.ADD_USER_ROLE, nativeQuery = true)
	void addRole(UUID userId, String roleName); 

	@Override
	@Transactional
	@Modifying
	@Query(value = Queries.REMOVE_USER_ROLES, nativeQuery = true)
	void removeRoles(UUID userId);

	@Override
	default SearchResult<? extends User> search(SearchFilter filter) {
		SearchResult<UserEntity> result = new SearchResult<>();
		EntityManager em = JpaUtils.getEntityManager();
		CriteriaBuilder cb = em.getCriteriaBuilder();

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);  
		countQuery.select(cb.count(countQuery.from(UserEntity.class)));
		result.setTotal(em.createQuery(countQuery).getSingleResult().intValue());		

		CriteriaQuery<UserEntity> query = cb.createQuery(UserEntity.class);
		query.from(UserEntity.class);
		result.setResult(em.createQuery(query)
				.setFirstResult(filter.getOffset())
				.setMaxResults(filter.getLimit())
				.getResultList());

		return result;
	}
}
