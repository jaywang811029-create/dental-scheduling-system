package com.example.demo.dto;

public class ShiftDetail {
  private String frontDesk1;
    private String chairsideName1;
    private String doctorName1;
    private String chairsideName2;
    private String doctorName2;
    private String chairsideName3;
    private String doctorName3;
    private String floaterName1;
    private String floaterName2;

    // Getters and Setters
    public String getFrontDesk1() {
        return frontDesk1 != null ? frontDesk1 : "無";
    }

    public void setFrontDesk1(String frontDesk1) {
        this.frontDesk1 = frontDesk1;
    }

    public String getChairsideName1() {
        return chairsideName1 != null ? chairsideName1 : "無";
    }

    public void setChairsideName1(String chairsideName1) {
        this.chairsideName1 = chairsideName1;
    }

    public String getDoctorName1() {
        return doctorName1 != null ? doctorName1 : "無";
    }

    public void setDoctorName1(String doctorName1) {
        this.doctorName1 = doctorName1;
    }

    public String getChairsideName2() {
        return chairsideName2 != null ? chairsideName2 : "無";
    }

    public void setChairsideName2(String chairsideName2) {
        this.chairsideName2 = chairsideName2;
    }

    public String getDoctorName2() {
        return doctorName2 != null ? doctorName2 : "無";
    }

    public void setDoctorName2(String doctorName2) {
        this.doctorName2 = doctorName2;
    }

    public String getChairsideName3() {
        return chairsideName3 != null ? chairsideName3 : "無";
    }

    public void setChairsideName3(String chairsideName3) {
        this.chairsideName3 = chairsideName3;
    }

    public String getDoctorName3() {
        return doctorName3 != null ? doctorName3 : "無";
    }

    public void setDoctorName3(String doctorName3) {
        this.doctorName3 = doctorName3;
    }

    public String getFloaterName1() {
        return floaterName1 != null ? floaterName1 : "無";
    }

    public void setFloaterName1(String floaterName1) {
        this.floaterName1 = floaterName1;
    }

    public String getFloaterName2() {
        return floaterName2 != null ? floaterName2 : "無";
    }

    public void setFloaterName2(String floaterName2) {
        this.floaterName2 = floaterName2;
    }
}
