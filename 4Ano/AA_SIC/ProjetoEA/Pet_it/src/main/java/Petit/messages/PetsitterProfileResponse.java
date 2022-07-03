package Petit.messages;

import Petit.classes.Petsitter;
import Petit.classes.Species;

import java.util.Set;

public class PetsitterProfileResponse {
    private Petsitter petsitter;
    private Set<Species> cliAnimals;


    public PetsitterProfileResponse(Petsitter petsitter, Set<Species> cliAnimals) {
        this.petsitter = petsitter;
        this.cliAnimals = cliAnimals;
    }

    public Petsitter getPetsitter() {
        return petsitter;
    }

    public void setPetsitter(Petsitter petsitter) {
        this.petsitter = petsitter;
    }

    public Set<Species> getCliAnimals() {
        return cliAnimals;
    }

    public void setCliAnimals(Set<Species> cliAnimals) {
        this.cliAnimals = cliAnimals;
    }
}
