package com.danielturato.dao;

import com.danielturato.model.Collaborator;
import com.danielturato.model.Project;
import com.danielturato.model.Role;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
public class ProjectDaoImpl extends BaseDao<Project> implements ProjectDao  {

    public ProjectDaoImpl() {
        clazz = Project.class;
    }

}
