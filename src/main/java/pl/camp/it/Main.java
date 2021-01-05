package pl.camp.it;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.NoResultException;
import java.util.List;

public class Main {

    static SessionFactory sessionFactory = null;
    public static void main(String[] args) {
        sessionFactory = new Configuration().configure().buildSessionFactory();
        User user = new User(0, "admin", "admin");
        saveUser(user);
        saveUser(new User(0, "mateusz", "mateusz"));
        saveUser(new User(0, "janusz", "janusz"));

        saveUser(new User(3, "janusz2", "janusz2"));
        updateUser(new User(3, "janusz2", "janusz2"));
        deleteUser(new User(3, "sdfgsdf", "sdfgsdfg"));

        System.out.println(getUserById(1));

        System.out.println(getAllUsers());
    }

    public static void saveUser(User user) {
        Session session = Main.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.save(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    public static void updateUser(User user) {
        Session session = Main.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.update(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }

    public static void deleteUser(User user) {
        Session session = Main.sessionFactory.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            session.delete(user);
            tx.commit();
        } catch (Exception e) {
            if(tx != null)
                tx.rollback();
        } finally {
            session.close();
        }
    }
    public static User getUserById(int id) {
        Session session = Main.sessionFactory.openSession();
        User user = null;
        try {
            Query<User> query = session.createQuery("FROM pl.camp.it.User WHERE id = " + id);
            user = query.getSingleResult();
        } catch (NoResultException e) {

        } finally {
            session.close();
        }
        return user;
    }

    public static List<User> getAllUsers() {
        Session session = Main.sessionFactory.openSession();
        Query<User> query = session.createQuery("FROM pl.camp.it.User");
        List<User> list = query.getResultList();
        session.close();
        return list;
    }
}
