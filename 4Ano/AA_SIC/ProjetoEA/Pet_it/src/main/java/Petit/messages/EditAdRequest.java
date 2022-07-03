package Petit.messages;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalTime;
import java.util.List;

public class EditAdRequest {
    private String email;
    private List<LocalTime> monday;
    private List<LocalTime> tuesday;
    private List<LocalTime> wednesday;
    private List<LocalTime> thursday;
    private List<LocalTime> friday;
    private List<LocalTime> saturday;
    private List<LocalTime> sunday;
    private List<Boolean> animals;
    private List<Boolean> services;
    private float price;

    public EditAdRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<LocalTime> getMonday() {
        return monday;
    }

    public void setMonday(List<LocalTime> monday) {
        this.monday = monday;
    }

    public List<LocalTime> getTuesday() {
        return tuesday;
    }

    public void setTuesday(List<LocalTime> tuesday) {
        this.tuesday = tuesday;
    }

    public List<LocalTime> getWednesday() {
        return wednesday;
    }

    public void setWednesday(List<LocalTime> wednesday) {
        this.wednesday = wednesday;
    }

    public List<LocalTime> getThursday() {
        return thursday;
    }

    public void setThursday(List<LocalTime> thursday) {
        this.thursday = thursday;
    }

    public List<LocalTime> getFriday() {
        return friday;
    }

    public void setFriday(List<LocalTime> friday) {
        this.friday = friday;
    }

    public List<LocalTime> getSaturday() {
        return saturday;
    }

    public void setSaturday(List<LocalTime> saturday) {
        this.saturday = saturday;
    }

    public List<LocalTime> getSunday() {
        return sunday;
    }

    public void setSunday(List<LocalTime> sunday) {
        this.sunday = sunday;
    }

    public List<Boolean> getAnimals() {
        return animals;
    }

    public void setAnimals(List<Boolean> animals) {
        this.animals = animals;
    }

    public List<Boolean> getServices() {
        return services;
    }

    public void setServices(List<Boolean> services) {
        this.services = services;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "EditAdRequest{" +
                "monday=" + monday +
                ", tuesday=" + tuesday +
                ", wednesday=" + wednesday +
                ", thursday=" + thursday +
                ", friday=" + friday +
                ", saturday=" + saturday +
                ", sunday=" + sunday +
                ", animals=" + animals +
                ", services=" + services +
                ", price=" + price +
                '}';
    }
}
