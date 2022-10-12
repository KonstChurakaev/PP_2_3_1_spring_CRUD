package jm.task.core.jdbc;

import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        SessionFactory factory=new Configuration()
//                .configure("hibernate.cfg.xml")
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




        User user2 = new User("Petr", "Smirnov", (byte) 24);
        User user3 = new User("Oleg", "Ivanov", (byte) 23);
        User user4 = new User("Olga", "Petrova", (byte) 19);



    }
}
