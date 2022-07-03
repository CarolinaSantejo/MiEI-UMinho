package Petit.database;


import Petit.classes.Animal;
import Petit.classes.Request;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RequestDAO {
    private static final DbConfig dbConfig = new DbConfig();


    public RequestDAO() {}

    public void save(Request c) {
        //3 - Session
        Session s = dbConfig.getNewSession();
        s.setFlushMode(FlushMode.COMMIT); //propagate changes on commit

        //4 - start the transaction
        Transaction t = s.beginTransaction();

        s.save(c.getServiceOptions());
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

    public Request get(int id) {
        Session s = dbConfig.getNewSession();

        Request request = (Request) s.get(Request.class, id);
        request.getAd().getSpecies().size();
        s.close();
        return request;
    }

    public List<Request> getAll() {
        Session s = dbConfig.getNewSession();

        Query query = s.createQuery("FROM Request");
        List<Request> results = query.list();
        return results;
    }
    public List<Request> getRequestsPetsitter(int adId){
        Session s = dbConfig.getNewSession();

        Query query = s.createQuery("FROM Request E where E.ad="+adId);
        List<Request> results = query.list();
        for (Request r : results) {
            r.getAd().getServices().size();
            r.getAd().getSpecies().size();
            r.setClientId(r.getClient().getUsername());
        }
        s.close();
        return results;
    }

}
