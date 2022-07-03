package Petit.classes;

import java.text.DecimalFormat;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

import Petit.beans.UserManager;
import Petit.database.*;
import Petit.util.Log;

public class Facade {
    private DbConfig dbConfig;
    private ClientDAO clients;
    private AnimalDAO animals;
    private PetsitterDAO petsitters;
    private UserManager userManager;
    private AdvertisementDAO ads;
    private RequestDAO req;
    private ServiceDAO services;

    public Facade() {
        try {
            dbConfig = new DbConfig();
            /*clients = new ClientDAO();
            petsitters = new PetsitterDAO();
            animals = new AnimalDAO();
            userManager = new UserManager();
            ads = new AdvertisementDAO();
            req = new RequestDAO();*/
            services = new ServiceDAO();
            petsitters = new PetsitterDAO();
            ads = new AdvertisementDAO();
        }
        catch (Exception e) {
            e.printStackTrace();
            System.out.println("Unable to connect to hibernate");
        }
    }

    public void addDefaultServices() {
        //services = new ServiceDAO();
        Generator g = new Generator();
        List<Service> servs = g.getDefaultServices();
        for (Service s : servs) {
            services.save(s);
        }
    }

    public void addPetsitters() {
        //petsitters = new PetsitterDAO();
        //ads = new AdvertisementDAO();
        Generator g = new Generator();
        List<Petsitter> petsitterList = g.getPetsitters();
        for(Petsitter p : petsitterList) {
            ads.save(p.getAd());
            petsitters.save(p);
        }
    }


    public void run() {
        //addDefaultServices();
        //addPetsitters();

        //services = new ServiceDAO();
        //System.out.println("Servicos");

        //System.out.println(services.getAll());

    }
}
