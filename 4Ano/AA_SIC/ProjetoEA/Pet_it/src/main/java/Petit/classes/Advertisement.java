package Petit.classes;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.persistence.*;

@Entity
public class Advertisement {
	@Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private int id;
    private float tax;

    @OneToMany(fetch = FetchType.EAGER)
	private Map<String, TimeInterval> availability;
    
	@Column
	@Enumerated(EnumType.STRING)
	@ElementCollection(targetClass = Species.class)
    private List<Species> species;

	@ManyToMany
    private List<Service> services;

    public Advertisement(){
        this.id = 0;
        this.tax = 0;
        this.availability = new HashMap<>();
        this.species = new ArrayList<>();
        this.services = new ArrayList<>();
    }

    public int getId() {
		return id;
	}
	
	public void setId(int id) {
		this.id = id;
	}

	public float getTax() {
		return tax;
	}

	public void setTax(float tax) {
		this.tax = tax;
	}

	public Map<String, TimeInterval> getAvailability() {
		return availability;
	}

	public void setAvailability(Map<String, TimeInterval> availability) {
		this.availability = availability;
	}

	public void setNewAvailability(Map<String, List<LocalTime>> availability) {
		this.availability = new HashMap<>();
		for(Map.Entry<String,List<LocalTime>> e : availability.entrySet()) {
			TimeInterval ti = new TimeInterval(e.getValue().get(0), e.getValue().get(1));
			this.availability.put(e.getKey(),ti);
		}
	}

	public void setSpecies(List<Species> species) {
		this.species = species;
	}
    
	public List<Species> getSpecies() {
		return species;
	}

	public List<Service> getServices() {
		return services;
	}

	public void setServices(List<Service> services) {
		this.services = services;
	}

	@Override
	public String toString() {
		return "Advertisement{" +
				"id=" + id +
				", tax=" + tax +
				", availability=" + availability +
				", species=" + species +
				", services=" + services +
				'}';
	}
}
