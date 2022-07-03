package Petit.classes;

import Petit.database.AdvertisementDAO;
import Petit.database.PetsitterDAO;
import Petit.database.ServiceDAO;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class Generator {

    public List<Service> getDefaultServices() {
        List<Service> services = new ArrayList<>();
        List<Price> prices = new ArrayList<>();
        prices.add(new Price("1-3", 3.0f));
        prices.add(new Price("4-12", 5.0f));
        prices.add(new Price("12-24", 8.0f));
        Service companion = new Service(prices, Service_Type.Companion);
        services.add(companion);

        prices = new ArrayList<>();
        prices.add(new Price("0-7", 10.0f));
        prices.add(new Price("7-15", 15.0f));
        prices.add(new Price("15-25", 20.0f));
        prices.add(new Price("25-35", 25.0f));
        prices.add(new Price(">35", 50.0f));
        Service bath = new Service(prices, Service_Type.Bath_Shear);
        services.add(bath);

        prices = new ArrayList<>();
        prices.add(new Price("Per hour", 6.0f));
        Service training = new Service(prices, Service_Type.Training);
        services.add(training);

        return services;
    }

    public List<Petsitter> getPetsitters() {
        List<Petsitter> petsitters = new ArrayList<>();

        String[] nomes = {"Lucília Cantanhede", "Baltasar Torquato", "Salomé Granjeiro", "Aman Muniz", "Maia Quirino",
                "Daniela Dinis", "Cláudia Lameira", "Márcia Couceiro", "Paula Barreiros", "Lucas Melo",
                "Maria Julia Correia", "Lucas Cunha", "Raul Campos", "Maria Teixeira", "Joaquim Peixoto",
                "Beatriz da Rosa", "Stella da Costa", "Laura Silveira", "Gustavo da Costa", "Marina Moreira"};
        String[] emails = {"lucy", "baltasarTorquato", "salomeGranjeiro", "amanMuniz", "maiaQuirino",
                "Daniela Dinis", "Cláudia Lameira", "Márcia Couceiro", "Paula Barreiros", "Lucas Melo",
                "Maria Julia Correia", "Lucas Cunha", "Raul Campos", "Maria Teixeira", "Joaquim Peixoto",
                "Beatriz da Rosa", "Stella da Costa", "Laura Silveira", "Gustavo da Costa", "Marina Moreira"};
        String[] cidades = {"Braga", "Lisboa", "Faro", "Viana do Castelo", "Bragança", "Porto", "Vila Real", "Coimbra", "Beja", "Évora"};

        List<Species> species = new ArrayList<>();
        species.add(Species.Cat);
        species.add(Species.Horse);
        species.add(Species.Dog);
        species.add(Species.Reptile);

        List<Service_Type> service_types = new ArrayList<>();
        service_types.add(Service_Type.Training);
        service_types.add(Service_Type.Bath_Shear);
        service_types.add(Service_Type.Companion);
        ServiceDAO servicesDAO = new ServiceDAO();
        List<Service> serv = new ArrayList<>();
        for(Service_Type st : service_types) {
            Service s = servicesDAO.getByServiceType(st);
            serv.add(s);
        }

        Map<String,List<LocalTime>> availability = new HashMap<>();
        List<LocalTime> l = new ArrayList<>();
        l.add(LocalTime.of(9,0));
        l.add(LocalTime.of(20,0));
        List<LocalTime> l2 = new ArrayList<>();
        l2.add(null);
        l2.add(null);
        availability.put("Monday", l);
        availability.put("Tuesday", l);
        availability.put("Wednesday", l);
        availability.put("Thursday", l);
        availability.put("Friday", l);
        availability.put("Saturday", l2);
        availability.put("Sunday", l2);

        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setDecimalSeparator('.');
        DecimalFormat df = new DecimalFormat("0.00");
        df.setDecimalFormatSymbols(symbols);


        for (int i = 0; i < nomes.length; i++) {
            String email = nomes[i].replaceAll("\\s+","").toLowerCase(Locale.ROOT) + "@gmail.com";
            email = Normalizer.normalize(email, Normalizer.Form.NFD).replaceAll("[^\\p{ASCII}]", "");
            Random random = new Random();
            List<Integer> index = new ArrayList<>();
            List<Service> services = new ArrayList<>();
            for (int j = 0; j < serv.size(); j++) {
                int x = random.nextInt(serv.size());
                if (!index.contains(x)) {
                    services.add(serv.get(x));
                }
                index.add(x);
            }
            index = new ArrayList<>();
            List<Species> animals = new ArrayList<>();
            for (int j = 0; j < species.size(); j++) {
                int x = random.nextInt(species.size());
                if (!index.contains(x)) {
                    animals.add(species.get(x));
                }
                index.add(x);
            }

            float tax = random.nextFloat(2.0f, 15.0f);
            tax = Float.parseFloat(df.format(tax));
            System.out.println(tax);
            String city = cidades[random.nextInt(cidades.length)];
            Advertisement ad = new Advertisement();
            ad.setTax(tax);
            ad.setNewAvailability(availability);
            ad.setSpecies(animals);
            ad.setServices(services);
            Petsitter p = new Petsitter(nomes[i],stringGenerator(10),email,900000000 + random.nextInt(100000000),"Portugal, " + city, dateGenerator(),"Hi, \n My name is " + nomes[i] + " and I love animals!", ad);
            petsitters.add(p);

        }
        return petsitters;
    }

    public LocalDate dateGenerator() {
        long minDay = LocalDate.of(1950, 1, 1).toEpochDay();
        long maxDay = LocalDate.of(2002, 12, 31).toEpochDay();
        long randomDay = ThreadLocalRandom.current().nextLong(minDay, maxDay);
        return LocalDate.ofEpochDay(randomDay);
    }

    public String stringGenerator(int length) {
        int leftLimit = 97; // letter 'a'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
                .limit(length)
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

}
