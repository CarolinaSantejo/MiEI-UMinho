package Petit.database;

import Petit.classes.*;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class AdvertisementDAO {
    private static final DbConfig dbConfig = new DbConfig();


    public AdvertisementDAO() {}

    public void save(Advertisement c) {
        //3 - Session
        Session s = dbConfig.getNewSession();
        s.setFlushMode(FlushMode.COMMIT); //propagate changes on commit

        //4 - start the transaction
        Transaction t = s.beginTransaction();

        for (TimeInterval ti : c.getAvailability().values()) {
            int id = (int) s.save(ti);
            ti.setId(id);
        }

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

    public Advertisement get(int id) {
        Session s = dbConfig.getNewSession();

        Advertisement ad = (Advertisement) s.get(Advertisement.class, id);
        ad.getSpecies().size();
        s.close();
        return ad;
    }


    public List<Advertisement> getAll() {
        Session s = dbConfig.getNewSession();

        Query query = s.createQuery("FROM Advertisement");
        List<Advertisement> results = query.list();
        return results;
    }
}
