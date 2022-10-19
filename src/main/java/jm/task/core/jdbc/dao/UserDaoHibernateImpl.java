package jm.task.core.jdbc.dao;


import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.persistence.criteria.CriteriaQuery;
import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {

    private Transaction transaction = null;
    private final String CREATE_TABLE = "CREATE TABLE IF NOT EXISTS users (" +
            "id BIGINT PRIMARY KEY AUTO_INCREMENT, " +
            "name VARCHAR(50) NOT NULL , " +
            "last_name VARCHAR(50) NOT NULL , " +
            "age INT NOT NULL )";
    private final String DROP_TABLE = "DROP TABLE IF EXISTS users";
    private final String CLEAN_TABLE = "TRUNCATE TABLE users";

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {

        try (SessionFactory sessionFactory = Util.getUtil().getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.createNativeQuery(CREATE_TABLE).executeUpdate();
            transaction.commit();
            System.out.println(" Users table successfully created");
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void dropUsersTable() {

        try (SessionFactory sessionFactory = Util.getUtil().getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.createNativeQuery(DROP_TABLE).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {

        try (SessionFactory sessionFactory = Util.getUtil().getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.save(new User(name, lastName, age));
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public void removeUserById(long id) {

        try (SessionFactory sessionFactory = Util.getUtil().getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            if (session.get(User.class, id) != null) {
                session.delete(session.get(User.class, id));
                transaction.commit();
                System.out.println("User c id =" + id + " deleted");
            } else {
                System.out.println("User c id =" + id + " not found");
            }
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();

        try (SessionFactory sessionFactory = Util.getUtil().getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            users = session.createQuery("FROM User").list();
            return users;
        } catch (HibernateException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void cleanUsersTable() {

        try (SessionFactory sessionFactory = Util.getUtil().getConnectionHibernate();
             Session session = sessionFactory.openSession()) {

            transaction = session.beginTransaction();
            session.createNativeQuery(CLEAN_TABLE).executeUpdate();
            transaction.commit();
        } catch (HibernateException e) {
            e.printStackTrace();
            if (transaction != null) {
                transaction.rollback();
            }
        }
    }
}
