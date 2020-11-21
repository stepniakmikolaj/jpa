package com.kodilla.jpa.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String addressLineOne;
    private String addressLineTwo;

    public Customer() {

    }

    public Customer(Long id, String name, String addressLineOne, String addressLineTwo) {
        this.id = id;
        this.name = name;
        this.addressLineOne = addressLineOne;
        this.addressLineTwo = addressLineTwo;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getAddressLineOne() {
        return addressLineOne;
    }

    public String getAddressLineTwo() {
        return addressLineTwo;
    }
}
