package com.rasbet.rasbet.BookmakerBL;

public class Competition {
    private String idCompetition;
    private String name;
    private String idSport;


    public Competition(String idCompetition, String name, String idSport) {
        this.idCompetition = idCompetition;
        this.name = name;
    }


    public String getIdCompetition() {
        return this.idCompetition;
    }

    public void setIdCompetition(String idCompetition) {
        this.idCompetition = idCompetition;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIdSport() {
        return this.name;
    }

    public void setIdSport(String name) {
        this.name = name;
    }

}