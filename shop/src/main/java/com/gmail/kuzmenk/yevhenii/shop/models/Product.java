package com.gmail.kuzmenk.yevhenii.shop.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private double weight;
    private double price;
    private String photoName;
    private String specification;

    public Product(String name, double weight, double price, String photoName, String specification){
        this.name=name;
        this.weight=weight;
        this.price=price;
        this.photoName=photoName;
        this.specification=specification;
    }


}
