import jm.task.core.jdbc.model.User;
import org.hibernate.HibernateException;
import org.hibernate.Metamodel;
import org.hibernate.query.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.Map;

public class Main {

    public static void main(final String[] args) throws Exception {
        SessionFactory factory=new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .buildSessionFactory();

        try {
            Session session = factory.getCurrentSession();
            User user1 = new User("Ivan", "Ivanovich", (byte) 44);
            session.beginTransaction();
            session.save(user1);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }
}
//        SessionFactory factory=new Configuration()
//                .configure("hibernate.cfg.xml")
//                .addAnnotatedClass(User.class)
//                .buildSessionFactory();
//
//        try {
//            Session session = factory.getCurrentSession();
//            User user1 = new User("Ivan", "Ivanovich", (byte) 44);
//            session.beginTransaction();
//            session.save(user1);
//            session.getTransaction().commit();
//        } finally {
//            factory.close();
//        }