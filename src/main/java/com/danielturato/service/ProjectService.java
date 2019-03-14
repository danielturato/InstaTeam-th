package com.danielturato.service;

import com.danielturato.model.Collaborator;
import com.danielturato.model.Project;
import com.danielturato.model.Role;

import java.util.List;

public interface ProjectService {
    List<Project> findAll();
    Project findById(Long id);
    void save(Project p);
    void delete(Project p);
    void removeCollaboratorFromProject(Collaborator c, Project p);
    void removeRoleFromProject(Role r, Project p);
}
