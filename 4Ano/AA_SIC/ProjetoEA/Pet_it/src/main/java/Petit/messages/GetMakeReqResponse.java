package Petit.messages;

import Petit.classes.Animal;
import Petit.classes.Request;
import Petit.classes.Service;
import Petit.classes.TimeInterval;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class GetMakeReqResponse {
    private Set<Animal> animals;
    private List<Service> services;
    private float tax;
    private Map<String, TimeInterval> availability;
    private List<Request> appointments;

    public GetMakeReqResponse() {
    }

    public GetMakeReqResponse(Set<Animal> animals, List<Service> services, float tax, Map<String, TimeInterval> availability, List<Request> appointments) {
        this.animals = animals;
        this.services = services;
        this.tax = tax;
        this.availability = availability;
        this.appointments = appointments;
    }

    public Map<String, TimeInterval> getAvailability() {
        return availability;
    }

    public void setAvailability(Map<String, TimeInterval> availability) {
        this.availability = availability;
    }

    public Set<Animal> getAnimals() {
        return animals;
    }

    public void setAnimals(Set<Animal> animals) {
        this.animals = animals;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public float getTax() {
        return tax;
    }

    public void setTax(float tax) {
        this.tax = tax;
    }

    public List<Request> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Request> appointments) {
        this.appointments = appointments;
    }
}
