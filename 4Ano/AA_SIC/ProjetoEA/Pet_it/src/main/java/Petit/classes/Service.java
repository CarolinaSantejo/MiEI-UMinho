package Petit.classes;

import java.util.List;
import java.util.Map;

import javax.persistence.*;

@Entity
@Inheritance(strategy=InheritanceType.JOINED)
public class Service {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    //private String type;

    @ManyToMany(fetch = FetchType.EAGER, targetEntity = Price.class, cascade = {CascadeType.ALL})
    private List<Price> price;

    @Enumerated(EnumType.STRING)
    private Service_Type serviceType;


    public Service(List<Price> price, Service_Type serviceType) {
        this.price = price;
        this.serviceType = serviceType;
    }

    public Service() {}

    public Service_Type getServiceType() {
        return serviceType;
    }

    public void setServiceType(Service_Type service) {
        this.serviceType = service;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Price> getPrice() {
        return price;
    }

    public void setPrice(List<Price> price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Service{" +
                "id=" + id +
                ", price=" + price +
                ", service=" + serviceType +
                '}';
    }
}
