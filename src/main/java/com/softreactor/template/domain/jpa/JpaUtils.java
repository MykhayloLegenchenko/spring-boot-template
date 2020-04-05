package com.softreactor.template.domain.jpa;

import javax.persistence.EntityManager;

import org.springframework.stereotype.Component;

/**
 * JPA related utility methods. 
 */
@Component
public class JpaUtils {

	private static EntityManager em;

	public JpaUtils(EntityManager em) {
		JpaUtils.em = em;
	}

	public static EntityManager getEntityManager() {
		return em;
	}

}
