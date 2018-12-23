package com.danielturato.dao;

import com.danielturato.model.Project;
import org.springframework.stereotype.Repository;

@Repository
public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao  {

    public ProjectDaoImpl() {
        clazz = Project.class;
    }
}
