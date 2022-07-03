package Petit.classes;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

import javax.persistence.*;

@Entity
public class Animal {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    private String name;

    @Enumerated(EnumType.STRING)
    private Species species;

    private String breed;
    private float weight;
    private int age;
    private int sex;
    private String diseases;
    private String extra;

    @ManyToOne
    @JoinColumn(name="client_id", nullable=false)
    private Client owner;



    public Animal() {

    }


    public Animal(Client c, String name, Species species, String breed, float weight, int age, int sex, String diseases, String extra) {
        this.name = name;
        this.species = species;
        this.weight = weight;
        this.breed=breed;
        this.age = age;
        this.sex = sex;
        this.diseases = diseases;
        this.extra = extra;
        this.owner = c;
    }

    @JsonIgnore
    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public String getBreed() {
        return this.breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Species getSpecies() {
        return this.species;
    }

    public void setSpecies(Species species) {
        this.species = species;
    }

    public float getWeight() {
        return this.weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
    

    public String getDiseases() {
        return this.diseases;
    }

    public void setDiseases(String diseases) {
        this.diseases = diseases;
    }

    public String getExtra() {
        return this.extra;
    }

    public void setExtra(String extra) {
        this.extra = extra;
    }
    

    @Override
    public String toString() {
        return "{" +
            " id='" + getId() + "'" +
            ", name='" + getName() + "'" +
            ", species='" + getSpecies() + "'" +
            ", weight='" + getWeight() + "'" +
            ", age='" + getAge() + "'" +
            ", sex='" + getSex() + "'" +
            ", diseases='" + getDiseases() + "'" +
            ", extra='" + getExtra() + "'" +
            "}";
    }


}
