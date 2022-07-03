package Petit.controller;

import Petit.beans.UserManager;
import Petit.classes.Client;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin("http://localhost:8081/")
public class Controller {

    @GetMapping("/clients")
    public List<Client> getClients(){
        System.out.println("listar clientes controller");

       return UserManager.getAllClients();

    }

}
