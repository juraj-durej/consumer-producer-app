package org.juraj.durej.app.database;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.juraj.durej.app.models.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UserRepository {

  private static final Logger log = LoggerFactory.getLogger(UserRepository.class);
  Transaction transaction = null;
  private Lock lock = new ReentrantLock();

  public void addUser(User user) {
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
      lock.lock();
      transaction = session.beginTransaction();
      session.persist(user);
      transaction.commit();

    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      log.error(e.getMessage());
    } finally {
      lock.unlock();
    }
  }

  public List<User> getUsers() {
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {

      return session.createQuery("SELECT u FROM User u", User.class).list();

    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      log.error(e.getMessage());
    }
    return List.of();
  }

  public void deleteAllUsers() {
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      lock.lock();
      session.createQuery("DELETE FROM User").executeUpdate();

      transaction.commit();

    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      log.error(e.getMessage());
    } finally {
      lock.unlock();
    }
  }

}
