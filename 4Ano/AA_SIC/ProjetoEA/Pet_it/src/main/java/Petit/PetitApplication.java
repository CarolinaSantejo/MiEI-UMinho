package Petit;

import Petit.classes.Facade;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PetitApplication {

	public static void main(String[] args) {
		Facade f = new Facade();
		f.run();
		SpringApplication.run(PetitApplication.class, args);

	}

}
