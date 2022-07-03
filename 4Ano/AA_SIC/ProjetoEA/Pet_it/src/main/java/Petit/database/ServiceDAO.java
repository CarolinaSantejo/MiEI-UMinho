package Petit.database;

import java.util.ArrayList;
import java.util.List;

import Petit.classes.*;
import org.hibernate.FlushMode;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;

public class ServiceDAO {
    private static final DbConfig dbConfig = new DbConfig();


    public ServiceDAO() { }

    public void save(Service p) {
        //3 - Session
        Session s = dbConfig.getNewSession();
        s.setFlushMode(FlushMode.COMMIT); //propagate changes on commit

        //4 - start the transaction
        Transaction t = s.beginTransaction();

        for(Price price : p.getPrice()) {
            s.save(price);
        }

        //5 - save the object
        s.save(p);

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

    public Service get(int id) {
        Session s = dbConfig.getNewSession();

        Service User = (Service) s.get(Service.class, id);
        s.close();
        return User;
    }

    public Service getByServiceType(Service_Type serviceType) {
        Session s = dbConfig.getNewSession();
        String q = "FROM Service E WHERE E.serviceType='"+ serviceType.name() + "'";
        Query query = s.createQuery(q);
        List<Service> results = query.list();
        s.close();
        List<Service> res = new ArrayList<>();
        for (Service sv : results) {
            if (!(sv instanceof ServiceOpt)) {
                res.add(sv);
            }
        }
        if(res.size()==1)
            return res.get(0);
        else return null;
    }

    public List<Service> getAll() {
        Session s = dbConfig.getNewSession();

        Query query = s.createQuery("FROM Service");
        List<Service> results = query.list();
        return results;
    }

}

