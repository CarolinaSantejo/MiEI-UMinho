package Petit.beans;

import Petit.classes.*;
import Petit.database.*;
import Petit.util.Log;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class RequestManager {

    private static ClientDAO clientes = new ClientDAO();
    private static PetsitterDAO petsitters = new PetsitterDAO();
    private static ServiceDAO services = new ServiceDAO();
    private static AdvertisementDAO advertisements = new AdvertisementDAO();
    private static RequestDAO requests = new RequestDAO();

    private static Lock lock = new ReentrantLock();
    private static String TAG = "[REQUESTMANAGER]";



    //public static List<Advertisement> getAds(){return ads.getAll();}

    @Bean
    public static void editAd(String email, Map<String,List<LocalTime>> availability, List<Species> species, List<Service_Type> service_types, float price) {
        Petsitter p = petsitters.get(email);
        Advertisement ad = p.getAd();
        ad.setTax(price);
        ad.setNewAvailability(availability);

        ad.setSpecies(species);
        List<Service> serv = new ArrayList<>();
        for(Service_Type st : service_types) {
            Service s = services.getByServiceType(st);
            serv.add(s);
        }
        ad.setServices(serv);
        advertisements.save(ad);
        petsitters.save(p);

        Log.i(TAG,"Atualizado com sucesso: " + petsitters.get(email).getAd().toString());
    }

    @Bean
    public static List<Petsitter> getAllPetsitters(){
        return petsitters.getAll();
    }

    @Bean
    public static List<Service> getServices(){
        List<Service> servs = services.getAll();
        List<Service> res = new ArrayList<>();
        for (Service s : servs) {
            if (!(s instanceof ServiceOpt)) {
                res.add(s);
            }
        }
        return res;
    }

    @Bean
    public static List<Request> getAppointments(String email){
        Petsitter p = petsitters.get(email);
        if (p != null) {
            return requests.getRequestsPetsitter(p.getAd().getId());
        }
        else return null;
    }

    @Bean
    public static void makeRequest(String email, String petsitterId, LocalDate date, LocalDate dateEnd, LocalTime startTime, LocalTime endTime, int petSelected, String serviceType, String opt, float total) throws Exception {
        lock.lock();
        Advertisement a = petsitters.get(petsitterId).getAd();
        List<Request> req = requests.getRequestsPetsitter(a.getId());
        LocalDateTime startDate = LocalDateTime.of(date,startTime);
        LocalDateTime endDate = LocalDateTime.of(dateEnd,endTime);


        for (Request r : req) {
            if ((startDate.isAfter(r.getStart_date()) || (startDate.equals(r.getStart_date()))) && ((startDate.isBefore(r.getEnd_date()) || (startDate.equals(r.getEnd_date()))))) {
                lock.unlock();
                throw new Exception("Make request not submitted");
            }
            if ((endDate.isAfter(r.getStart_date()) || (endDate.equals(r.getStart_date()))) && ((endDate.isBefore(r.getEnd_date()) || (endDate.equals(r.getEnd_date()))))) {
                lock.unlock();
                throw new Exception("Make request not submitted");
            }
        }
        Client c = clientes.get(email);

        Animal animal = null ;
        for (Animal an : c.getAnimals()) {
            if (an.getId() == petSelected) {
                animal = an;
                break;
            }
        }
        Service s = services.getByServiceType(Service_Type.valueOf(serviceType));
        List<Price> p = new ArrayList<>();
        for (Price price : s.getPrice()) {
            p.add(new Price(price.getOpt(), price.getPrice()));
        }
        s.setPrice(p);
        ServiceOpt serviceOpt = new ServiceOpt(s, opt);

        Request r = new Request(startDate,endDate,total, serviceOpt, animal, a, c);
        requests.save(r);
        lock.unlock();
        Log.i(TAG,"Request adicionado com sucesso: ");
    }
}
