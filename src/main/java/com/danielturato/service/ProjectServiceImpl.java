package com.danielturato.service;

import com.danielturato.dao.ProjectDaoImpl;
import com.danielturato.model.Collaborator;
import com.danielturato.model.Project;
import com.danielturato.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDaoImpl projectDao;

    @Override
    public List<Project> findAll() {
        return projectDao.findAll().stream().sorted(Comparator.comparing(Project::getDate)).collect(Collectors.toList());
    }

    @Override
    public Project findById(Long id) {
        return projectDao.findById(id);
    }

    @Override
    public void save(Project p) {
        projectDao.save(p);
    }

    @Override
    public void delete(Project p) {
        projectDao.delete(p);
    }

    @Override
    public void removeCollaboratorFromProject(Collaborator c, Project p) {
        List<Collaborator> collaborators = p.getCollaborators();

        List<Collaborator> newCollaborators = new ArrayList<>();
        for (Collaborator co : collaborators) {
            if (!(co.getId().equals(c.getId()))) {
                newCollaborators.add(co);
            }
        }

        p.setCollaborators(newCollaborators);
        save(p);
    }

    @Override
    public void removeRoleFromProject(Role r, Project p) {
        List<Role> rolesNeeded = p.getRolesNeeded();

        List<Role> newRoles = new ArrayList<>();
        for (Role ro : rolesNeeded) {
            if (!(ro.getId().equals(r.getId()))) {
                newRoles.add(ro);
            }
        }

        p.setRolesNeeded(newRoles);
        save(p);
    }

}
