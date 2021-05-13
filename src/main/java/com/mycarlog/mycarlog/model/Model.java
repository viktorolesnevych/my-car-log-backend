package com.mycarlog.mycarlog.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

@Entity
@Table(name="models")
public class Model {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String name;

    @Column
    private String modelClass;

    @Column
    private String imgLink;

    @JsonIgnore // To prevent StackOverflow when calling each other
    @ManyToOne
    @JoinColumn(name="brand_id")
    private Brand brand;

    public Model(){

    }
    public Model(Long id, String name, String modelClass, String imgLink) {
        this.id = id;
        this.name = name;
        this.modelClass = modelClass;
        this.imgLink = imgLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModelClass() {
        return modelClass;
    }

    public void setModelClass(String modelClass) {
        this.modelClass = modelClass;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }

    @Override
    public String toString() {
        return "Model{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", modelClass='" + modelClass + '\'' +
                ", imgLink='" + imgLink + '\'' +
                '}';
    }

    public Brand getBrand() {
        return brand;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }
}
