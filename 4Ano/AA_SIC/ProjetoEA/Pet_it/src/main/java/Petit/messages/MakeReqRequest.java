package Petit.messages;

import java.time.LocalDate;
import java.time.LocalTime;

public class MakeReqRequest {
    private String email;
    private String petsitterId;
    private LocalDate date;
    private LocalDate endDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private int petSelected;
    private String serviceType;
    private String opt;
    private float total;

    public MakeReqRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPetsitterId() {
        return petsitterId;
    }

    public void setPetsitterId(String petsitterId) {
        this.petsitterId = petsitterId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getPetSelected() {
        return petSelected;
    }

    public void setPetSelected(int petSelected) {
        this.petSelected = petSelected;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public float getTotal() {
        return total;
    }

    public void setTotal(float total) {
        this.total = total;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "MakeReqRequest{" +
                "email='" + email + '\'' +
                ", petsitterId='" + petsitterId + '\'' +
                ", date=" + date +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", petSelected=" + petSelected +
                ", serviceType='" + serviceType + '\'' +
                ", opt='" + opt + '\'' +
                ", total=" + total +
                '}';
    }
}
