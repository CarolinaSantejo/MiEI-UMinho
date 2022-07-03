package Petit.classes;

import java.time.LocalDate;
import javax.persistence.*;

@Entity
public class Petsitter extends User {
    private LocalDate birth_date;
    //private float rating;
    private String about_me;
    @OneToOne
    private Advertisement ad;

    public Petsitter(){
        super();
        this.birth_date = null;
        this.about_me = "";
        this.ad = null;
    }

    public Petsitter(String username, String password, String email, int phone, String location, LocalDate birth_date, String about_me, Advertisement ad) {
        super(username, password, email, phone, location);
        this.birth_date = birth_date;
        //this.rating = rating;
        this.about_me = about_me;
        this.ad = ad;
    }


    public Petsitter(LocalDate birth_date, String about_me, Advertisement ad) {
        this.birth_date = birth_date;
        this.about_me = about_me;
        this.ad = ad;
    }



    public LocalDate getBirth_date() {
        return this.birth_date;
    }

    public void setBirth_date(LocalDate birth_date) {
        this.birth_date = birth_date;
    }

    public String getAbout_me() {
        return this.about_me;
    }

    public void setAbout_me(String about_me) {
        this.about_me = about_me;
    }

    public Advertisement getAd() {
        return this.ad;
    }

    public void setAd(Advertisement ad) {
        this.ad = ad;
    }

    @Override
    public String toString() {
        return super.toString() +
                " birth_date=" + birth_date +
                ", about_me='" + about_me + '\'' +
                ", ad=" + ad +
                '}';
    }
}
