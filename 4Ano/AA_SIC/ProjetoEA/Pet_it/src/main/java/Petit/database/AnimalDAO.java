package Petit.database;

import java.util.List;

import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Petit.classes.Animal;

public class AnimalDAO {
    private static final DbConfig dbConfig = new DbConfig();


    public AnimalDAO() { }
    
    public boolean save(Animal c) {
        //3 - Session
        Session s = dbConfig.getNewSession();
        s.setFlushMode(FlushMode.COMMIT); //propagate changes on commit
        
        //4 - start the transaction
        Transaction t = s.beginTransaction();

        //5 - save the object
        s.save(c);
    
        try {
            //6 - commit the transaction
            t.commit();
            s.close();
            return true;
        } catch (Exception e) {
            //6 - rollback in case of exception
            t.rollback();
            e.printStackTrace();
            System.out.println("Unable to commit changes");
            s.close();
            return false;
        }


    }

    public Animal get(int id) {
        Session s = dbConfig.getNewSession();

        Animal User = (Animal) s.get(Animal.class, id);
        s.close();
        return User;
    }

    public List<Animal> getAll() {
        Session s = dbConfig.getNewSession();

        Query query = s.createQuery("FROM Animal");
        List<Animal> results = query.list();
        s.close();
        return results;
    }

    public List<Animal> getAnimaisClient(String email){
        Session s = dbConfig.getNewSession();

        Query query = s.createQuery("FROM Animal where client_id="+email);
        List<Animal> results = query.list();
        s.close();
        return results;
    }

    
}
