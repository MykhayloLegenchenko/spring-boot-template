package com.softreactor.template.domain;

import java.util.Optional;
import java.util.UUID;

/**
 * Base domain repository interface. 
 */
public interface BaseRepository<T> {

	/**
	 * Creates new object.
	 */
	T createObject();

	/**
	 * Finds object by id.
	 */
	Optional<? extends T> findById(UUID id);

	/**
	 * Saves object to database. 
	 */
	void persist(T entity);

}