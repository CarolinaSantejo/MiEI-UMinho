package Petit.beans;

import Petit.classes.*;
import Petit.database.AdvertisementDAO;
import Petit.database.AnimalDAO;
import Petit.database.ClientDAO;
import Petit.database.PetsitterDAO;
import Petit.util.Log;
import org.springframework.context.annotation.Bean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;


public class UserManager {

    private static ClientDAO clientes = new ClientDAO();
    private static AnimalDAO animals = new AnimalDAO();

    private static AdvertisementDAO ads = new AdvertisementDAO();
    private static PetsitterDAO petsitters = new PetsitterDAO();
    private static String TAG = "[USERMANAGER]";

    @Bean
    public static User getUser(String email) {
        Client c= clientes.get(email);
        Petsitter p= null;
        if(c!=null){
            return c;
        } else if ((p= petsitters.get(email))!=null)
            return p;
        else return null;
    }

    @Bean
    public static Petsitter getPetsitter(String email){
        return petsitters.get(email);
    }

    @Bean
    public static Advertisement getPetsitterAd(String email){
        return petsitters.get(email).getAd();
    }

    @Bean
    public static Client getClient(String email){
        return clientes.get(email);
    }


    @Bean
    public static Set<Animal> getAnimals(String email) {
        Client c = clientes.get(email);
        if( c != null) return c.getAnimals();
        else return null;
    }

    @Bean
    public static Set<Animal> getAnimalsSpecies(String email, List<Species> species) {
        Client c = clientes.get(email);
        if(c != null) {
            Set<Animal> animals = c.getAnimals();
            Set<Animal> animalsSpecies = new HashSet<>();
            for (Animal a : animals) {
                if(species.contains(a.getSpecies())) {
                    animalsSpecies.add(a);
                }
            }
            return animalsSpecies;
        }
        else return null;
    }

    @Bean
    public static Set<Request> getRequests(String email){
        Client c = clientes.get(email);
        if (c != null) {
            Set<Request> requests = c.getRequests();
            for (Request r : requests) {
                Petsitter petsitter = petsitters.getPetsitterByAd(r.getAd().getId());
                r.setPetsitter(petsitter.getUsername());
            }
            return requests;
        }
        else return null;
    }



    @Bean
    public static List<Client> getAllClients(){
        return  clientes.getAll();
    }

    public static User login(String email, String password) throws Exception {
        Client cliente = clientes.get(email);
        Petsitter p = petsitters.get(email);
        if(cliente==null && p==null) {
            Log.e("[DB]", "Não existe utilizador com este email");
            throw new Exception("Não existe nenhum utilizador com esse email");
        }
        else if (cliente!=null && p==null) {
            String pass = cliente.getPassword();
            if (!password.equals(pass)) {
                Log.w("[DB]", "Password Errada");
                throw new Exception("Password inserida está errada");
            } else return cliente;
        }
        else {
            String pass = p.getPassword();
            if (!password.equals(pass)) {
                Log.w("[DB]", "Password Errada");
                throw new Exception("Password inserida está errada");
            } else return p;
        }
    }

    @Bean
    public static Client registerClient(String usermane, String email, String password,
                                        int phone, String location) throws Exception {
        //verificar se ainda n existe nenhum utilizador com o mesmmo email
        Client cliente = clientes.get(email);
        Petsitter p = petsitters.get(email);
        if(cliente!=null || p!=null){
            Log.e("[DB]", "Já existe utilizador com este email");
            throw new Exception("Já existe um utilizador com esse email");
        }
        else {
            Client c = new Client(usermane, password, email, phone, location, new HashSet<>(), new HashSet<>());
            clientes.save(c);
            return c;
        }
    }

    @Bean
    public static Petsitter registerPetsitter(String usermane, String email, String password, int phone, String location,
                                              LocalDate birthdate) throws Exception {
        //verificar se ainda n existe nenhum utilizador com o mesmmo email
        Client cliente = clientes.get(email);
        Petsitter p = petsitters.get(email);
        if(cliente != null || p != null) {
            Log.e("[DB]", "Já existe utilizador com este email");
            throw new Exception("Já existe um utilizador com esse email");
        }
        else {
            Advertisement ad = new Advertisement();
            ads.save(ad);
            Petsitter petsitter = new Petsitter(usermane, password, email, phone, location, birthdate, "", ad);
            petsitters.save(petsitter);
            return petsitter;
        }
    }

    public static void setAboutPetsitter(String email, String about_me) throws Exception {
        Petsitter p = petsitters.get(email);
        if(p == null) {
            Log.e("[DB]", "Já existe utilizador com este email");
            throw new Exception("Já existe um utilizador com esse email");
        }
        else {
            p.setAbout_me(about_me);
            petsitters.save(p);
        }
    }


    @Bean
    public static boolean createAnimalProfile(String email, String name, Species species, String breed, Float weight,
                                           int age,  int sex, String diseases, String extra) {
        Client c = clientes.get(email);
        Animal a = new Animal(c, name, species, breed, weight, age, sex, diseases, extra);
        return animals.save(a);
    }



}
