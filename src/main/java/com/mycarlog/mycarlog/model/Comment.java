package com.mycarlog.mycarlog.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String textContent;

    @Column
    private Date dateCreated;

    @JsonIgnore // Always in the opposite side of the mapping
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnore // Always in the opposite side of the mapping
    @ManyToOne
    @JoinColumn(name = "log_id")
    private Log log;


    public Comment() {
    }

    public Comment(Long id, String textContent, Date dateCreated, User user, Log log) {
        this.id = id;
        this.textContent = textContent;
        this.dateCreated = dateCreated;
        this.user = user;
        this.log = log;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTextContent() {
        return textContent;
    }

    public void setTextContent(String textContent) {
        this.textContent = textContent;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public User getUser() {
        return user;
    }

    public Long getUser_id() {
        return user.getId();
    }

    public Long getLog_id() {
        return log.getId();
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Log getLog() {
        return log;
    }

    public void setLog(Log log) {
        this.log = log;
    }
}
