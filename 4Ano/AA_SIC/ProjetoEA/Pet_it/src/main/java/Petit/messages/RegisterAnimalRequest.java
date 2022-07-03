package Petit.messages;

public class RegisterAnimalRequest {


    private String email;
    private String name;
    private String species;
    private String breed;
    private Float weight;
    private int age;
    private int ageMonths;
    private int sex;
    private String diseases;
    private String notes;

    public RegisterAnimalRequest(String email, String name, String species, String breed, Float weight, int age, int ageMonths, int sex, String disease, String extras) {
        this.email = email;
        this.name = name;
        this.species = species;
        this.breed = breed;
        this.weight = weight;
        this.age = age;
        this.ageMonths = ageMonths;
        this.sex = sex;
        this.diseases = disease;
        this.notes = extras;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSpecies() {
        return species;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Float getWeight() {
        return weight;
    }

    public void setWeight(Float weight) {
        this.weight = weight;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public String getDiseases() {
        return diseases;
    }

    public void setDiseases(String disease) {
        this.diseases = disease;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String extras) {
        this.notes = extras;
    }

    public int getAgeMonths() {
        return ageMonths;
    }

    public void setAgeMonths(int ageMonths) {
        this.ageMonths = ageMonths;
    }


    @Override
    public String toString() {
        return "RegisterAnimalRequest{" +
                "email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", species='" + species + '\'' +
                ", breed='" + breed + '\'' +
                ", weight=" + weight +
                ", age=" + age +
                ", ageMonths=" + ageMonths +
                ", sex=" + sex +
                ", diseases='" + diseases + '\'' +
                ", extras='" + notes + '\'' +
                '}';
    }
}
