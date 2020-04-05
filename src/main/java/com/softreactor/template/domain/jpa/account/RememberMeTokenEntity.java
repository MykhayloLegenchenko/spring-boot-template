package com.softreactor.template.domain.jpa.account;

import java.time.Instant;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

import com.softreactor.template.domain.account.RememberMeToken;
import com.softreactor.template.domain.jpa.BaseJpaEntity;

/**
 * Remember me token entity class for JPA.
 */
@Entity(name = "RememberMeToken")
@Table(indexes = @Index(columnList = "username"))
public class RememberMeTokenEntity extends BaseJpaEntity implements RememberMeToken {

	@Column(nullable = false)
	private String username;

	@Column(nullable = false, columnDefinition = "binary(16)")
	private UUID token;

	@Column(nullable = false, columnDefinition = "timestamp")
	private Instant date;

	@Override
	public String getUsername() {
		return username;
	}

	@Override
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public UUID getToken() {
		return token;
	}

	@Override
	public void setToken(UUID token) {
		this.token = token;
	}

	@Override
	public Instant getDate() {
		return date;
	}

	@Override
	public void setDate(Instant date) {
		this.date = date;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = super.hashCode();
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((token == null) ? 0 : token.hashCode());
		result = prime * result + ((username == null) ? 0 : username.hashCode());
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
		RememberMeTokenEntity other = (RememberMeTokenEntity) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (token == null) {
			if (other.token != null)
				return false;
		} else if (!token.equals(other.token))
			return false;
		if (username == null) {
			if (other.username != null)
				return false;
		} else if (!username.equals(other.username))
			return false;
		return true;
	}

}
