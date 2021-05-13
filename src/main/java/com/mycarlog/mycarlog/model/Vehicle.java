package com.mycarlog.mycarlog.model;


import javax.persistence.*;

@Entity
@Table(name="vehicles")
public class Vehicle {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String color;

    @Column
    private String description;

    @Column
    private String imgLink;

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", description='" + description + '\'' +
                ", imgLink='" + imgLink + '\'' +
                '}';
    }

    public Vehicle(){}


    public Vehicle(Long id, String color, String description, String imgLink) {
        this.id = id;
        this.color = color;
        this.description = description;
        this.imgLink = imgLink;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImgLink() {
        return imgLink;
    }

    public void setImgLink(String imgLink) {
        this.imgLink = imgLink;
    }
}
