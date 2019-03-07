package com.danielturato.dao;

import com.danielturato.model.Collaborator;
import com.danielturato.model.Project;
import com.danielturato.model.Role;

import java.util.List;

public interface ProjectDao {
    List<Project> findAll();
    Project findById(Long id);
    void save(Project p);
    void delete(Project p);
}
