package Petit.classes;

import java.util.List;
import java.util.Map;
import java.util.Set;


import javax.persistence.*;


@Entity
public class Client extends User {
    @OneToMany(mappedBy ="owner", fetch = FetchType.EAGER)
    @MapKey(name = "id")
    Set<Animal> animals;
    @OneToMany(mappedBy ="client", fetch = FetchType.EAGER)
    @MapKey(name = "id")
    Set<Request> requests;


    public Client() {
    }
    

    public Client(String username, String password, String email, int phone, String location, Set<Animal> animals, Set<Request> requests) {
        super(username, password, email, phone, location);
        this.animals = animals;
        this.requests = requests;
    }
    


    public Set<Animal> getAnimals() {
        return this.animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }
    


    public Set<Request> getRequests() {
        return this.requests;
    }

    public void setRequests(Set<Request> requests) {
        this.requests = requests;
    }
    
    


    @Override
    public String toString() {
        return super.toString() +
            " animals='" + getAnimals() + "'" +
            ", requests='" + getRequests() + "'" +
            "}";
    }

}
