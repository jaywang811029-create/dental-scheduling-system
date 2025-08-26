package com.example.demo.jpa;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Doctors")
public class Doctors {

    @Id // 標記為主鍵
    private String id;
    private String name;

     public Doctors(String id, String name) {
        this.id = id;
        this.name = name;
    }

     public Doctors(){
     }

     public String getId() {
         return id;
     }

     public void setId(String id) {
         this.id = id;
     }

     public String getName() {
         return name;
     }

     public void setName(String name) {
         this.name = name;
     }

     @Override
     public String toString() {
        return "Doctors [id=" + id + ", name=" + name + "]";
     }

}
