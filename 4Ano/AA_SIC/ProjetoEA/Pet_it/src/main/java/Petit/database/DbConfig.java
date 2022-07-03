package Petit.database;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

import Petit.classes.*;

public class DbConfig {
    private SessionFactory sf;
    private StandardServiceRegistry sr;


    public DbConfig() {
        //1 - Configuration
        Configuration configuration = new Configuration().configure();
        sr = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        
        // ADD ENTITY CLASSES HERE
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Client.class);
        configuration.addAnnotatedClass(Animal.class);
        configuration.addAnnotatedClass(Price.class);
        configuration.addAnnotatedClass(Service.class);
        configuration.addAnnotatedClass(ServiceOpt.class);
        configuration.addAnnotatedClass(Request.class);
        configuration.addAnnotatedClass(Petsitter.class);
        configuration.addAnnotatedClass(Advertisement.class);
        configuration.addAnnotatedClass(TimeInterval.class);




        //2 - SessionFactory
        sf = configuration.buildSessionFactory(sr);
        
    }

    public Session getNewSession() {
        return sf.openSession();
    }
    
    public void close() {
        //7 - Close the session and end process
        StandardServiceRegistryBuilder.destroy(sr);
    }
}
