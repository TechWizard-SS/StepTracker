package StepTracker_Pac;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration()
                    .configure("hibernate.cfg.xml")
                    .addAnnotatedClass(StepData.class)
                    .buildSessionFactory();

        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }


    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutDown(){
        getSessionFactory().close();
    }
}
