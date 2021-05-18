package com.mycarlog.mycarlog.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

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
    private String nickName;

    @Column
    private String description;

    @Column
    private String imgLink;

    @OneToMany(mappedBy = "vehicle", orphanRemoval = true)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Log> logList;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name="user_id")
    private User user;

    @JsonIgnore // Always in the opposite side of the mapping
    @ManyToOne
    @JoinColumn(name="model_id")
    private Model model;



    public Vehicle(){}


    public Vehicle(Long id, String color, String nickName, String description, String imgLink) {
        this.id = id;
        this.color = color;
        this.nickName = nickName;
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

    public List<Log> getLogList() {
        return logList;
    }

    public void setLogList(List<Log> logList) {
        this.logList = logList;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Model getModel() {
        return model;
    }

    public void setModel(Model model) {
        this.model = model;
    }


    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Vehicle{" +
                "id=" + id +
                ", color='" + color + '\'' +
                ", nickName='" + nickName + '\'' +
                ", description='" + description + '\'' +
                ", imgLink='" + imgLink + '\'' +
                ", logList=" + logList +
                ", user=" + user +
                ", model=" + model +
                '}';
    }
}
