package com.danielturato.web;

import com.danielturato.model.Project;
import com.danielturato.service.ProjectServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @RequestMapping("/projects")
    public String getProjects(ModelMap model) {
        List<Project> projects = projectService.findAll();
        model.put("projects", projects);

        return "index";
    }

    @RequestMapping("/projects/create")
    public String addProject() {
        return "edit_project";
    }
}
