package com.danielturato.service;

import com.danielturato.dao.ProjectDaoImpl;
import com.danielturato.model.Project;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectDaoImpl projectDao;

    @Override
    public List<Project> findAll() {
        return projectDao.findAll();
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
}
