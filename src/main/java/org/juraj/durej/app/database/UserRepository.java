package org.juraj.durej.app.database;

import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.juraj.durej.app.models.User;

public class UserRepository {

  Transaction transaction = null;
//  je tento lock potrebny ? Budem/Nebudem blokovat databazu?
  private Lock lock = new ReentrantLock();

  public void addUser(User user){
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
      lock.lock();
      transaction = session.beginTransaction();
      session.persist(user);
      transaction.commit();

    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
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
      e.printStackTrace();
    }
    return List.of();
  }

  public void deleteAllUsers(){
    try (Session session = HibernateUtils.getSessionFactory().openSession()) {
      transaction = session.beginTransaction();
      lock.lock();
      session.createQuery("DELETE FROM User").executeUpdate();

      transaction.commit();

    } catch (Exception e) {
      if (transaction != null) {
        transaction.rollback();
      }
      e.printStackTrace();
    } finally {
      lock.unlock();
    }
  }

}
