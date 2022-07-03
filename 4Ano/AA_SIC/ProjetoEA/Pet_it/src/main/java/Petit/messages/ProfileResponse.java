package Petit.messages;

import Petit.classes.Animal;
import Petit.classes.Request;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ProfileResponse {
    private List<Animal> animals;
    private List<Request> requests;

    public ProfileResponse() {
    }

    public ProfileResponse(List<Animal> animals, List<Request> requests) {
        this.animals = animals;
        this.requests = requests;
    }

    public List<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Animal> animals) {
        this.animals = animals;
    }

    public List<Request> getRequests() {
        return requests;
    }

    public void setRequests(List<Request> requests) {
        this.requests = requests;
    }
}
