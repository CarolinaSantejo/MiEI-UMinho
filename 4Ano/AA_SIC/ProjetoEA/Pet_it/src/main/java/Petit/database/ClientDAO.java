package Petit.database;

import java.util.List;
import java.util.Set;

import Petit.classes.Request;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import Petit.classes.Client;

public class ClientDAO {
    private static final DbConfig dbConfig= new DbConfig();


    public ClientDAO() {}
    
    public static void save(Client c) {
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
        } catch (Exception e) {
            //6 - rollback in case of exception
            t.rollback();
            e.printStackTrace();
            System.out.println("Unable to commit changes");
        }

        s.close();
    }

    public Client get(String id) {
        Session s = dbConfig.getNewSession();

        Client User = s.get(Client.class, id);
        if (User != null) {
            for (Request r : User.getRequests()) {
                r.getAd().getSpecies().size();
                r.getAd().getServices().size();

            }
        }


        s.close();
        return User;
    }

    public Client getByEmail(String email) {
        Session s = dbConfig.getNewSession();
        String q = "FROM Client E WHERE E.email="+ email;
        Query query = s.createQuery(q);
        List<Client> results = query.list();
        s.close();
        if(results.size()==1)
            return results.get(0);
        else return null;
    }


    public List<Client> getAll() {
        Session s = dbConfig.getNewSession();
        
        Query query = s.createQuery("FROM Client");
        List<Client> results = query.list();
        s.close();
        return results;
    }
}
