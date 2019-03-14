package com.danielturato.model;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.context.annotation.PropertySource;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@PropertySource("messages.properties")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 3, max = 20, message = "{project.name.size}")
    private String name;

    @NotNull
    @Size(min = 3, max = 50, message = "{project.desc.size}")
    private String desc;

    @NotNull
    private String status;

    @Temporal(TemporalType.DATE)
    private Date date = new Date();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Role> rolesNeeded = new ArrayList<>();

    @ManyToMany
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Collaborator> collaborators = new ArrayList<>();

    public Project() {}

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

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Role> getRolesNeeded() {
        return rolesNeeded;
    }

    public void setRolesNeeded(List<Role> rolesNeeded) {
        this.rolesNeeded = rolesNeeded;
    }

    public List<Collaborator> getCollaborators() {
        return collaborators;
    }

    public void setCollaborators(List<Collaborator> collaborators) {
        this.collaborators = collaborators;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
