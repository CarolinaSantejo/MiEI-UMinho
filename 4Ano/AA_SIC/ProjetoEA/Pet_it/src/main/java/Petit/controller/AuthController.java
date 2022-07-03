package Petit.controller;

import Petit.beans.RequestManager;
import Petit.beans.UserManager;
import Petit.classes.*;
import Petit.messages.*;
import Petit.util.Log;

import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.mashape.unirest.http.HttpResponse;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:8081/")
public class AuthController {

    private static final String TAG = "[AUTH_CONTROLLER]";
    @PostMapping("/login")
    public ResponseEntity loginCliente(@RequestBody AuthenticationRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        Log.i(TAG, email + ": " + password);
        try{
            AuthenticationResponse aR = new AuthenticationResponse();
            User u = UserManager.login(email, password);
            if(u.getClass().getSimpleName().equals("Petsitter")){
                Log.i(TAG, "É um petsitter");
                aR = new AuthenticationResponse(true,"Petsitter", u.getUsername());
            }
            else if(u.getClass().getSimpleName().equals("Client")){
                Log.i(TAG, "É um client");
                aR = new AuthenticationResponse(true,"Client", u.getUsername());
            }
            Log.i(TAG, "Bem Sucedido!");
            return ResponseEntity.ok(aR);


        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            return ResponseEntity.badRequest().body("Email ou password inválido");
        }
    }

    @PostMapping("/registerClient")
    public ResponseEntity registerCliente(@RequestBody RegisterClientRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        String username = request.getUsername();
        int phone = request.getPhone();
        String location = request.getLocation();

        Log.i(TAG, "{email:" + email + ", password: " + password+ ", username:" + username +
                ", phone: "+phone + ", location: " + location + "}");
        try{
            Client c = UserManager.registerClient(username, email, password, phone, location);
            RegisterResponse rr = new RegisterResponse(true);
            Log.i(TAG, "Registo do cliente bem Sucedido!");
            System.out.println(rr.toString());
            return ResponseEntity.ok(rr);
        } catch (Exception e ){
            return ResponseEntity.badRequest().body("Utilizador com esse email já existe no sistema");
        }
    }


    @PostMapping("/registerPetsitter")
    public ResponseEntity registerPetsitter(@RequestBody RegisterPetsitterRequest request){
        String email = request.getEmail();
        String password = request.getPassword();
        String username = request.getUsername();
        int phone = request.getPhone();
        String location = request.getLocation();
        LocalDate birthdate = request.getBirthdate();

        Log.i(TAG, "{email:" + email + ", password: " + password+ ", username:" + username +
                ", phone: "+phone + ", location: " + location + ", birthdate:"+ birthdate+  "}");
        try{
            Petsitter p = UserManager.registerPetsitter(username, email, password, phone, location, birthdate);
            RegisterResponse rr = new RegisterResponse(true);
            Log.i(TAG, "Registo do petsitter bem Sucedido!");
            return ResponseEntity.ok(rr);
        } catch (Exception e ){
            return ResponseEntity.badRequest().body("Utilizador com esse email já existe no sistema");
        }
    }

    @PostMapping("/registerAnimal")
    public ResponseEntity registerAnimal(@RequestBody RegisterAnimalRequest request){
        System.out.println(request.toString());
        String email = request.getEmail();
        String name = request.getName();
        Species species = Species.valueOf(request.getSpecies());
        String breed = request.getBreed();
        Float weight = request.getWeight();
        int age = request.getAge();
        int sex = request.getSex();
        String disease = request.getDiseases();
        String extras = request.getNotes();

        boolean res = UserManager.createAnimalProfile(email, name, species, breed, weight, age, sex, disease, extras);
        if(res==true) {
            SimpleResponse rr = new SimpleResponse(true);
            Log.i(TAG, "Criação do perfil do Animal bem sucedido!");
            return ResponseEntity.ok(rr);
        } else return ResponseEntity.badRequest().body("Não foi possível registar o animal");

    }

    @PostMapping("/clientProfile")
    public ResponseEntity clientProfile(@RequestBody ProfileRequest request) {
        String email = request.getEmail();
        Log.i(TAG,"Entrou: " + email);
        List<Animal> animals = new ArrayList<>(Objects.requireNonNull(UserManager.getAnimals(email))) ;
        List<Request> requests = new ArrayList<>(Objects.requireNonNull(UserManager.getRequests(email)));
        System.out.println("an: " + animals.toString());
        System.out.println("req: " + requests.toString());
        ProfileResponse pr = new ProfileResponse(animals,requests);
        ResponseEntity<ProfileResponse> r = ResponseEntity.ok(pr);
        Log.i(TAG,"Entrou: " + r.toString());
        return ResponseEntity.ok(pr);
    }

    @PostMapping("/petsitterProfile")
    public Petsitter petsitterProfile(@RequestBody ProfileRequest request) {
        return UserManager.getPetsitter(request.getEmail());
    }

    @PostMapping("/petsitterProfileReq")
    public ResponseEntity petsitterProfile(@RequestBody GetMakeReqRequest request) {
        Petsitter p = UserManager.getPetsitter(request.getEmailPetsitter());
        Set<Animal> animals = UserManager.getAnimals(request.getEmail());
        Set<Species> species = new HashSet<>();
        for (Animal a : animals) {
            species.add(a.getSpecies());
        }
        return ResponseEntity.ok(new PetsitterProfileResponse(p, species));
    }

    @PostMapping("/editAd")
    public ResponseEntity editAd(@RequestBody EditAdRequest request) {
        System.out.println(request.toString());
        Map<String,List<LocalTime>> availability = new HashMap<>();
        availability.put("Monday", request.getMonday());
        availability.put("Tuesday", request.getTuesday());
        availability.put("Wednesday", request.getWednesday());
        availability.put("Thursday", request.getThursday());
        availability.put("Friday", request.getFriday());
        availability.put("Saturday", request.getSaturday());
        availability.put("Sunday", request.getSunday());
        List<Species> species = new ArrayList<>();
        if (request.getAnimals().get(0)) {
            species.add(Species.Dog);
        }
        if (request.getAnimals().get(1)) {
            species.add(Species.Cat);
        }
        if (request.getAnimals().get(2)) {
            species.add(Species.Reptile);
        }
        if (request.getAnimals().get(3)) {
            species.add(Species.Horse);
        }
        List<Service_Type> service_types = new ArrayList<>();
        if (request.getServices().get(0)) {
            service_types.add(Service_Type.Companion);
        }
        if (request.getServices().get(1)) {
            service_types.add(Service_Type.Bath_Shear);
        }
        if (request.getServices().get(2)) {
            service_types.add(Service_Type.Training);
        }
        System.out.println("Especies: " + species);
        System.out.println("Servicos: " + service_types);
        RequestManager.editAd(request.getEmail(),availability, species, service_types, request.getPrice());

        SimpleResponse sr = new SimpleResponse(true);

        return ResponseEntity.ok(sr);
    }

    @PostMapping("/appointments")
    public ResponseEntity getAppointments(@RequestBody ProfileRequest request) {
        List<Request> reqs = RequestManager.getAppointments(request.getEmail());
        System.out.println(reqs);
        return ResponseEntity.ok(reqs);
    }

    @PostMapping("/getMakeRequest")
    public ResponseEntity getMakeRequestData(@RequestBody GetMakeReqRequest request) {

        Petsitter p = UserManager.getPetsitter(request.getEmailPetsitter());
        Set<Animal> animals = UserManager.getAnimalsSpecies(request.getEmail(), p.getAd().getSpecies());
        List<Request> reqs = RequestManager.getAppointments(request.getEmailPetsitter());

        GetMakeReqResponse rp = new GetMakeReqResponse(animals, p.getAd().getServices(), p.getAd().getTax(), p.getAd().getAvailability(), reqs);

        return ResponseEntity.ok(rp);
    }

    @PostMapping("/makeRequest")
    public ResponseEntity makeRequest(@RequestBody MakeReqRequest request) {

        System.out.println(request);
        try {
            RequestManager.makeRequest(request.getEmail(),request.getPetsitterId(), request.getDate(), request.getEndDate(), request.getStartTime(), request.getEndTime(), request.getPetSelected(), request.getServiceType(), request.getOpt(), request.getTotal());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        SimpleResponse sr = new SimpleResponse(true);

        return ResponseEntity.ok(sr);
    }

    @PostMapping("/cities")
    public List<String> getCountryCities(@RequestBody String country){
        List<String> cities = new ArrayList<>();
        Unirest.setTimeouts(0, 0);
        try {
            System.out.println(country);
            if (country.charAt(country.length()-1)=='=') {
                country = country.replace(country.substring(country.length() - 1), "");
            }
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get("https://countriesnow.space/api/v0.1/countries/cities/q?country=" + country)
                    .header("accept", "application/json")
                    .asJson();

            JSONArray results = jsonResponse.getBody().getObject().getJSONArray("data");
            for (int i = 0; i < results.length(); ++i) {
                String loc = results.getString(i);
                cities.add(loc);
            }

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(cities);
        return cities;
    }



    @PostMapping("/aboutMe")
    public ResponseEntity setAboutMe_Petsiiter(@RequestBody AboutMeRequest about){
        try {
            UserManager.setAboutPetsitter(about.getEmail(), about.getAbout_text());
            SimpleResponse sr = new SimpleResponse(true);

            return ResponseEntity.ok(sr);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @PostMapping("/getAd")
    public ResponseEntity getAd(@RequestBody ProfileRequest request){
        try {
            Advertisement a = UserManager.getPetsitterAd(request.getEmail());
            return ResponseEntity.ok(a);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    /**
     *
     * ------------- GET MAPPING METHODS -----------------
     *
     */


    @GetMapping("/allPetsitters")
    public List<Petsitter> getAllPetS(){
        System.out.println("listar todos os petsitters");
        return RequestManager.getAllPetsitters();
    }

    @GetMapping("/servicePrices")
    public List<Service> getServices() {
        return RequestManager.getServices();
    }


    @GetMapping("/allCountries")
    public List<String> getAllCountries()  {
        List<String> countries = new ArrayList<>();
        Unirest.setTimeouts(0, 0);
        try {
            HttpResponse<JsonNode> jsonResponse
                    = Unirest.get("https://countriesnow.space/api/v0.1/countries")
                    .header("accept", "application/json")
                    .asJson();

            JSONArray results = jsonResponse.getBody().getObject().getJSONArray("data");
            for (int i = 0; i < results.length(); ++i) {
                JSONObject rec = results.getJSONObject(i);
                String loc = rec.getString("country");
                countries.add(loc);
            }

        } catch (UnirestException e) {
            throw new RuntimeException(e);
        }
        System.out.println(countries);
        return countries;
    }

}