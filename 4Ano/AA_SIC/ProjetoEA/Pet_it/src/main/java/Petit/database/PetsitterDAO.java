package Petit.database;


import Petit.classes.Petsitter;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class PetsitterDAO {
    private static final DbConfig dbConfig = new DbConfig();


    public PetsitterDAO() {}

    public void save(Petsitter c) {
        //3 - Session
        Session s = dbConfig.getNewSession();
        s.setFlushMode(FlushMode.COMMIT); //propagate changes on commit

        //4 - start the transaction
        Transaction t = s.beginTransaction();

        //5 - save the object
        s.saveOrUpdate(c);

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


    public Petsitter get(String id) {
        Session s = dbConfig.getNewSession();

        Petsitter petsitter = s.get(Petsitter.class, id);
        if (petsitter != null) {
            petsitter.getAd().getServices().size();
            petsitter.getAd().getSpecies().size();
        }
        s.close();
        return petsitter;
    }

    public Petsitter getByEmail(String email) {
        Session s = dbConfig.getNewSession();
        String q = "FROM Petsitter E WHERE E.email="+ email;
        Query query = s.createQuery(q);
        List<Petsitter> results = query.list();
        s.close();
        if(results.size()==1)
            return results.get(0);
        else return null;
    }

    public Petsitter getPetsitterByAd(int adId) {
        Session s = dbConfig.getNewSession();
        String q = "FROM Petsitter E WHERE E.ad="+ adId;
        Query query = s.createQuery(q);
        List<Petsitter> results = query.list();
        if(results.size()==1)
            return results.get(0);
        else return null;
    }

    public List<Petsitter> getAll() {
        Session s = dbConfig.getNewSession();

        Query query = s.createQuery("FROM Petsitter");
        List<Petsitter> results = query.list();
        return results;
    }
}
