package Petit.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
public class Request {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;

    private LocalDateTime start_date;

    private LocalDateTime end_date;

    private float final_price;

    @OneToOne
    private ServiceOpt serviceOptions;

    @OneToOne
    private Animal animal;

    @OneToOne
    private Advertisement ad;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client client;

    @Transient
    private String petsitter;

    @Transient
    private String clientId;

    public Request() {

    }

    public Request(LocalDateTime start_date, LocalDateTime end_date, float final_price, ServiceOpt serviceOptions, Animal animal, Advertisement ad, Client c) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.final_price = final_price;
        this.serviceOptions = serviceOptions;
        this.animal = animal;
        this.ad = ad;
        this.client = c;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getStart_date() {
        return this.start_date;
    }

    public void setStart_date(LocalDateTime start_date) {
        this.start_date = start_date;
    }

    public LocalDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(LocalDateTime end_date) {
        this.end_date = end_date;
    }

    public float getFinal_price() {
        return this.final_price;
    }

    public void setFinal_price(float final_price) {
        this.final_price = final_price;
    }

    public ServiceOpt getServiceOptions() {
        return serviceOptions;
    }

    public void setServiceOptions(ServiceOpt serviceOptions) {
        this.serviceOptions = serviceOptions;
    }

    public Animal getAnimal() {
        return this.animal;
    }

    public void setAnimal(Animal animal) {
        this.animal = animal;
    }

    @JsonIgnore
    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Advertisement getAd() {
        return ad;
    }

    public void setAd(Advertisement ad) {
        this.ad = ad;
    }

    public String getPetsitter() {
        return petsitter;
    }

    public void setPetsitter(String petsitter) {
        this.petsitter = petsitter;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }
}

