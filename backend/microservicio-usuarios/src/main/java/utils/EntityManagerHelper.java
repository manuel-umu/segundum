package utils;

import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class EntityManagerHelper {
	private static EntityManagerFactory entityManagerFactory;
	private static final ThreadLocal<EntityManager> entityManagerHolder;

	static {
		// Mapa propiedades de variables de entorno
		Map<String, String> overrides = new HashMap<>();
		addIfSet(overrides, "javax.persistence.jdbc.url", "DB_URL");
		addIfSet(overrides, "javax.persistence.jdbc.user", "DB_USER");
		addIfSet(overrides, "javax.persistence.jdbc.password", "DB_PASSWORD");
		addIfSet(overrides, "javax.persistence.jdbc.driver", "DB_DRIVER");

		entityManagerFactory = Persistence.createEntityManagerFactory("db_user", overrides);
		entityManagerHolder = new ThreadLocal<EntityManager>();
	}

	private static void addIfSet(Map<String, String> map, String property, String envVar) {
		String value = System.getenv(envVar);
		if (value != null)
			map.put(property, value);
	}

	public static EntityManager getEntityManager() {
		EntityManager entityManager = entityManagerHolder.get();
		if (entityManager == null || !entityManager.isOpen()) {
			entityManager = entityManagerFactory.createEntityManager();
			entityManagerHolder.set(entityManager);
		}
		return entityManager;
	}

	public static void closeEntityManager() {
		EntityManager entityManager = entityManagerHolder.get();
		if (entityManager != null) {
			entityManagerHolder.set(null);
			entityManager.close();
		}
	}
}