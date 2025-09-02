package com.example.demo.jpa;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ResultSchedule")
public class ResultSchedule {

    @EmbeddedId
    private ResultScheduleKey key;

    private String doctorName1;
    private String doctorName2;
    private String doctorName3;
    private String frontDesk1;
    private String frontDesk2;
    private String frontDesk3;
    private String chairsideName1;
    private String chairsideName2;
    private String chairsideName3;
    private String floaterName1;
    private String floaterName2;
    private String floaterName3;
    private String supportName1;
    private String supportName2;

    public ResultSchedule(ResultScheduleKey key, String doctorName1, String doctorName2, String doctorName3,
            String frontDesk1, String frontDesk2, String frontDesk3, String chairsideName1, String chairsideName2,
            String chairsideName3, String floaterName1, String floaterName2, String floaterName3, String supportName1,
            String supportName2) {
        this.key = key;
        this.doctorName1 = doctorName1;
        this.doctorName2 = doctorName2;
        this.doctorName3 = doctorName3;
        this.frontDesk1 = frontDesk1;
        this.frontDesk2 = frontDesk2;
        this.frontDesk3 = frontDesk3;
        this.chairsideName1 = chairsideName1;
        this.chairsideName2 = chairsideName2;
        this.chairsideName3 = chairsideName3;
        this.floaterName1 = floaterName1;
        this.floaterName2 = floaterName2;
        this.floaterName3 = floaterName3;
        this.supportName1 = supportName1;
        this.supportName2 = supportName2;
    }

    public ResultSchedule() {
    }

    public String getSupportName1() {
        return supportName1;
    }

    public void setSupportName1(String supportName1) {
        this.supportName1 = supportName1;
    }

    public String getSupportName2() {
        return supportName2;
    }

    public void setSupportName2(String supportName2) {
        this.supportName2 = supportName2;
    }

    public String getDoctorName1() {
        return doctorName1;
    }

    public void setDoctorName1(String doctorName1) {
        this.doctorName1 = doctorName1;
    }

    public String getDoctorName2() {
        return doctorName2;
    }

    public void setDoctorName2(String doctorName2) {
        this.doctorName2 = doctorName2;
    }

    public String getDoctorName3() {
        return doctorName3;
    }

    public void setDoctorName3(String doctorName3) {
        this.doctorName3 = doctorName3;
    }

    public String getChairsideName1() {
        return chairsideName1;
    }

    public void setChairsideName1(String chairsideName1) {
        this.chairsideName1 = chairsideName1;
    }

    public String getChairsideName2() {
        return chairsideName2;
    }

    public void setChairsideName2(String chairsideName2) {
        this.chairsideName2 = chairsideName2;
    }

    public String getChairsideName3() {
        return chairsideName3;
    }

    public void setChairsideName3(String chairsideName3) {
        this.chairsideName3 = chairsideName3;
    }

    public String getFloaterName1() {
        return floaterName1;
    }

    public void setFloaterName1(String floaterName1) {
        this.floaterName1 = floaterName1;
    }

    public String getFloaterName2() {
        return floaterName2;
    }

    public void setFloaterName2(String floaterName2) {
        this.floaterName2 = floaterName2;
    }

    public String getFloaterName3() {
        return floaterName3;
    }

    public void setFloaterName3(String floaterName3) {
        this.floaterName3 = floaterName3;
    }

    public ResultScheduleKey getkey() {
        return key;
    }

    public void setkey(ResultScheduleKey key) {
        this.key = key;
    }

    public ResultScheduleKey getKey() {
        return key;
    }

    public void setKey(ResultScheduleKey key) {
        this.key = key;
    }

    public String getFrontDesk1() {
        return frontDesk1;
    }

    public void setFrontDesk1(String frontDesk1) {
        this.frontDesk1 = frontDesk1;
    }

    public String getFrontDesk2() {
        return frontDesk2;
    }

    public void setFrontDesk2(String frontDesk2) {
        this.frontDesk2 = frontDesk2;
    }

    public String getFrontDesk3() {
        return frontDesk3;
    }

    public void setFrontDesk3(String frontDesk3) {
        this.frontDesk3 = frontDesk3;
    }

    @Override
    public String toString() {
        return "ResultSchedule [key=" + key + ", doctorName1=" + doctorName1 + ", doctorName2=" + doctorName2
                + ", doctorName3=" + doctorName3 + ", frontDesk1=" + frontDesk1 + ", frontDesk2=" + frontDesk2
                + ", frontDesk3=" + frontDesk3 + ", chairsideName1=" + chairsideName1 + ", chairsideName2="
                + chairsideName2 + ", chairsideName3=" + chairsideName3 + ", floaterName1=" + floaterName1
                + ", floaterName2=" + floaterName2 + ", floaterName3=" + floaterName3 + ", supportName1=" + supportName1
                + ", supportName2=" + supportName2 + "]";
    }

}
