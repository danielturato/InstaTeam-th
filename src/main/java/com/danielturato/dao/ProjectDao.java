package com.danielturato.dao;

import com.danielturato.model.Project;

import java.util.List;

public interface ProjectDao {
    List<Project> findAll();
    Project findById(Long id);
    void save(Project p);
    void delete(Project p);
}
