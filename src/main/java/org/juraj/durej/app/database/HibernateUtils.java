package org.juraj.durej.app.database;

import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HibernateUtils {

  private static final Logger log = LoggerFactory.getLogger(HibernateUtils.class);
  private static StandardServiceRegistry registry;
  private static SessionFactory sessionFactory;

  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {

        registry = new StandardServiceRegistryBuilder().configure().build();
        MetadataSources sources = new MetadataSources(registry);
        Metadata metadata = sources.getMetadataBuilder().build();
        sessionFactory = metadata.getSessionFactoryBuilder().build();

      } catch (Exception e) {
        log.error(e.getMessage());
        StandardServiceRegistryBuilder.destroy(registry);
      }
    }
    return sessionFactory;
  }

  public static void shutdown() {
    StandardServiceRegistryBuilder.destroy(registry);
  }
}
