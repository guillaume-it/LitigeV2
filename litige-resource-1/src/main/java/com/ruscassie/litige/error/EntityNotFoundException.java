package com.ruscassie.litige.error;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import org.springframework.util.StringUtils;

/**
 * Thanks to Bruno Cleite
 */
public class EntityNotFoundException extends RuntimeException {

	private static String generateMessage(final String entity, final Map<String, String> searchParams) {
		return StringUtils.capitalize(entity) + " was not found for parameters " + searchParams;
	}

	private static <K, V> Map<K, V> toMap(final Class<K> keyType, final Class<V> valueType, final Object... entries) {
		if (entries.length % 2 == 1)
			throw new IllegalArgumentException("Invalid entries");
		return IntStream.range(0, entries.length / 2).map(i -> i * 2).collect(HashMap::new,
				(m, i) -> m.put(keyType.cast(entries[i]), valueType.cast(entries[i + 1])), Map::putAll);
	}

	public EntityNotFoundException(final Class clazz, final String... searchParamsMap) {
		super(EntityNotFoundException.generateMessage(clazz.getSimpleName(),
				toMap(String.class, String.class, searchParamsMap)));
	}

}