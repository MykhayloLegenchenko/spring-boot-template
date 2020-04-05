package com.softreactor.template.domain;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

import org.springframework.util.Assert;

/**
 * Domain related utility methods.
 */
public class DomainUtils {

	/**
	 * Generates new entity id.
	 */
	public static UUID generateId() {
		return UUID.randomUUID();
	}

	/**
	 * Converts UUID identifier to Base64-encoded string.
	 */
	public static String uuidToString(UUID uuid) {
		return new String(Base64.getEncoder().encode(uuidToBytes(uuid)));
	}

	/**
	 * Converts Base64-encoded string to UUID identifier.
	 */
	public static UUID uuidFromString(String uuid) {
		return uuidFromBytes(Base64.getDecoder().decode(uuid)); 
	}

	public static byte[] uuidToBytes(UUID uuid) {
		ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
		bb.putLong(uuid.getMostSignificantBits());
		bb.putLong(uuid.getLeastSignificantBits());

		return bb.array();
	}
	
	public static UUID uuidFromBytes(byte[] bytes) {
		Assert.isTrue(bytes.length == 16, "Incorrect array size");

		ByteBuffer byteBuffer = ByteBuffer.wrap(bytes);
	    Long high = byteBuffer.getLong();
	    Long low = byteBuffer.getLong();

	    return new UUID(high, low);
	}
	}
