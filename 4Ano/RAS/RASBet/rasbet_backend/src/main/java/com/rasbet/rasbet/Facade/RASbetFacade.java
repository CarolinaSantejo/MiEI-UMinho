package com.rasbet.rasbet.Facade;

import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import com.rasbet.rasbet.BookmakerBL.BookmakerFacade;
import com.rasbet.rasbet.UserBL.UserFacade;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.web.bind.annotation.*;

/**
 * Facade of the system. Joins each subsystem facade.
 */
@CrossOrigin
@RestController
public class RASbetFacade {

    private BookmakerFacade bFacade;
    private UserFacade uFacade;

    public RASbetFacade() {
        bFacade = new BookmakerFacade();
        uFacade = new UserFacade();
    }

    /**
     * Métodos principais que fazem a junção dos métodos de ambos os subsistemas
     *
     * @throws ParseException
     */

    // -------------------------------------------------------------------------------------------------//

    // 1 - Done - Faz o registo do utilizador
    @PutMapping(path = "/auth/register")
    public String register(@RequestBody UserDTO userDTO)  {

        System.out.println("========== Registering new User ==========");
        System.out.println("=> User ID: " + userDTO.getIdUser());
        System.out.println("=> User Name: " + userDTO.getName());
        System.out.println("=> User Email: " + userDTO.getEmail());
        System.out.println("=> User Password: " + userDTO.getPassword());
        System.out.println("=> User DOB: " + userDTO.getDob());
        System.out.println("=============================================");


        String idUser = this.uFacade.addNewUser(
                userDTO.getIdUser(),
                userDTO.getName(),
                userDTO.getEmail(),
                userDTO.getPassword(),
                LocalDate.parse(userDTO.getDob()));

        return "OK";
    }

    // 2 - Done - Faz o login do utilizador
    @PostMapping(path = "/auth/login")
    public String login(@RequestBody UserDTO userCredentials)  {
        System.out.println(userCredentials);

        String[] tokens = userCredentials.getEmail().split("@");
        if (tokens[1].equals("rasbet.com")) {
            System.out.println("ADMIN");
            if(this.bFacade.verifyBookmaker(userCredentials.getEmail(),userCredentials.getPassword())){
                return this.bFacade.getBookmakerID(userCredentials.getEmail(), userCredentials.getPassword());
            }

        } else if(this.uFacade.verifyUser(userCredentials.getEmail(), userCredentials.getPassword())) {
            System.out.println("USER");
            if(this.uFacade.verifyUser(userCredentials.getEmail(), userCredentials.getPassword())) {
                return this.uFacade.getUserID(userCredentials.getEmail(), userCredentials.getPassword());
            }
        }
        return "fail";

    }

    // 3 - Done - Pede o perfil do utilizador para mostrar no frontend : Nome,
    // E-mail, Password
    @GetMapping(path = "/profile")
    public Map<String, String> requestProfile(@RequestParam String userID) {
        System.out.println("User " + userID + " trying to get profile");

        Map<String, String> userProfile = new HashMap<>();
        userProfile = this.uFacade.getUser(userID);
        return userProfile;
    }

    // 4 - O utilizador pode atualizar o seu email e password - Done
    @PostMapping(path = "/profile/update")
    public void updateUserEmail(@RequestBody UserDTO userData, @RequestParam String action, @RequestParam String data) {
        System.out.println("Updating user :");
        System.out.println(userData.toString());
        System.out.println(data);
        System.out.println(action);

        if (action.equals("EMAIL")) {
            System.out.println("Updated email");
            // this.uFacade.updateUser(userData.getIdUser(), data, userData.getName(), userData.getPassword());
        }
        else if (action.equals("PASSWORD")) {
            System.out.println("Updated password");
            // this.uFacade.updateUser(userData.getIdUser(), userData.getEmail(), userData.getName(), data);
        }
    }

    // 5 - Devolver a carteira do utilizador
    @GetMapping(path = "/wallet")
    public Map<String, BigDecimal> requestUserWallet(@RequestParam String userID) {
        System.out.println("Getting wallet of user " + userID);

        /*
        Estou a  cirar dados temporários.
        Temos de retornar algo assim, mas com dados reais.
        HashMap<String, BigDecimal> userWallet = new HashMap<>();
        userWallet.put("EUR", new BigDecimal(20.50));
        userWallet.put("USD", new BigDecimal(5.25));
        userWallet.put("BTC", new BigDecimal(3.8));
        return userWallet;
                 */

        Map<String, BigDecimal> userWallet = this.uFacade.checkWallet(userID);
       return userWallet;
    }

    /*
     * 0 = widthdraw
     * 1 = deposit
     */
    // 9 -
    @PostMapping(path = "/wallet/update" )
    public void updateWallet(@RequestParam String userID, @RequestBody WalletUpdateDTO updateDTO)
    {
        System.out.println("User " + userID + " is trying to update " + updateDTO.getAction() + " for " + updateDTO.getQuantity() + " " + updateDTO.getCoin());

        if (updateDTO.getAction() == 0)
            this.uFacade.widthdraw(userID, new BigDecimal(updateDTO.getQuantity()), updateDTO.getCoin());
        else if (updateDTO.getAction() == 1)
            this.uFacade.deposit(userID, new BigDecimal(updateDTO.getQuantity()), updateDTO.getCoin());

    }

    // 6 - Devolver o histórico e as estatisticas do won e lost
    @GetMapping(path = "/record")
    public Map<String, Object> requestUserRecord(@RequestParam String userID) {
        System.out.println("User " + userID + " is trying to access record.");

        Map<String, Object> userRecord = this.uFacade.checkRecords(userID);
        return userRecord;
    }

    // ------------------------------------------------------------------------------------------------------//

    @GetMapping(path = "/events")
    public Map<String, List<Object>> requestAllEvents(@RequestParam String userID) {
        System.out.println("Fetching all events for id " + userID );

        List<Object> events = this.bFacade.getFeed();
        System.out.println(events.toString());

        HashMap<String, List<Object>> ret = new HashMap<>();
        ret.put("events", events);
        return ret;
    }





    // 8 - Submete as apostas de um utilizador
    /**
     * choiceEvent: idChoice -> [idEvent, idCurrency]
     * oddsAmount: idChoice -> [Odd, Amount]
     */

    @PutMapping(path = "/bet/submit")
    public void submitBettingSlip(@RequestParam String userID, @RequestBody  ArrayList<BetDTO> bets) {
        System.out.println(userID + " submitting bet slip");
        System.out.println(bets);

        // String idUser, Map<String, List<String>> choiceEvent,
        //Map<String, List<Float>> oddsAmount) {
        /*
        try {
            uFacade.addUserBets(betDTO.g, choiceEvent, oddsAmount);
        } catch (NoMoneyException e) {

        }
        */
    }



    // 7 - vai buscar os eventos à bd
    @GetMapping(path = "/bookmaker/events")
    public List<Object> requestBookmakerBook(@RequestParam String bookmakerID) {
        List<String> events = new ArrayList<>();
        List<String> sports = new ArrayList<>();

        List<String> comps = new ArrayList<>();
        ArrayList<Object> ret = new ArrayList<>();

        System.out.println("Retrieving event book of bookmaker " + bookmakerID);

        events = this.bFacade.checkBook(bookmakerID);
        ret.add(events);

        return ret;
    }

    // 9 -
    @PutMapping(path = "/bookmaker/events/submit")
    public void submitEvent(@RequestBody EventDTO event) {
        /*
        List<String> ps = new ArrayList<>();
        ps.add("FCP");
        ps.add("SLB");
        Map<String,Float> cs = new HashMap<>();
        cs.put("1", (float)1.54);
        cs.put("X", (float)1.23);
        cs.put("2", (float)1.72);

        String str = "2022-02-02 14:30";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(str, formatter);

        this.bFacade.addNewEvent("CL", ps, dateTime, cs, "admin1");
        */

        this.bFacade.addNewEvent(event.getIdCompetition(), event.getParticipants(), event.getDate(), event.getChoices(), event.getIdBookamker());
    }

    // 10 -
    public void closeEvent(String idEvent, String result, Set<String> winnerChoices) {
        bFacade.closeEvent(idEvent, result);
        for (String choice : winnerChoices) {
            uFacade.updateWinners(choice);
        }
    }

    // 11 -
    public void updateEvent(String idEvent, String description, Map<String, Float> choices) {

    }

    // 12 -
    public void updateBetStatus(String idEvent, Map<String, Integer> bets) {

    }

}
