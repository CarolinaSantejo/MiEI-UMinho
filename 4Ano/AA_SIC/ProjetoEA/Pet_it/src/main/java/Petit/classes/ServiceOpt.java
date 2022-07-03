package Petit.classes;

import javax.persistence.Entity;
import java.util.ArrayList;
import java.util.List;


@Entity
public class ServiceOpt extends Service {
    private String servOption;

    public ServiceOpt() {
        super();
    }

    public ServiceOpt(Service s, String servOption) {
        super(s.getPrice(),s.getServiceType());
        this.servOption = servOption;
    }

    public String getServOption() {
        return servOption;
    }

    public void setServOption(String servOption) {
        this.servOption = servOption;
    }

}
