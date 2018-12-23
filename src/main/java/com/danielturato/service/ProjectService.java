package com.danielturato.service;

import com.danielturato.model.Project;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project findById(Long id);
    void save(Project p);
    void delete(Project p);
}
