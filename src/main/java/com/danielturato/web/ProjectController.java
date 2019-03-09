package com.danielturato.web;

import com.danielturato.model.Collaborator;
import com.danielturato.model.Project;
import com.danielturato.model.Role;
import com.danielturato.service.CollaboratorServiceImpl;
import com.danielturato.service.ProjectServiceImpl;
import com.danielturato.service.RoleServiceImpl;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.jws.WebParam;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private CollaboratorServiceImpl collaboratorService;

    @RequestMapping("/projects")
    public String getProjects(ModelMap model) {
        List<Project> projects = projectService.findAll();
        model.put("projects", projects);

        return "index";
    }

    @RequestMapping("/projects/create")
    public String addProject(ModelMap model) {

        if (!model.containsAttribute("project")) {
            model.put("project", new Project());
        }

        model.put("roles", roleService.findAll());

        return "edit_project";
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {

            result.getAllErrors().forEach(System.out::println);
            return "redirect:/projects/create";
        }

        projectService.save(project);

        return "redirect:/projects";
    }

    @RequestMapping("/projects/{id}")
    @Transactional
    public String viewProject(@PathVariable Long id, ModelMap model) {
        Project p = projectService.findById(id);
        model.put("project", p);
        model.put("roleToC", mapRoles(p.getRolesNeeded(), p.getCollaborators()));


        return "project_detail";
    }

    @RequestMapping("/projects/{id}/collaborators")
    @Transactional
    public String changeCollaborators(@PathVariable Long id, ModelMap model) {
        Project p = projectService.findById(id);
        model.put("project", p);
        model.put("roleToC", mapRoles(p.getRolesNeeded(), p.getCollaborators()));

        model.put("allCollaborators", collaboratorService.findAll());
        model.put("action", String.format("/projects/%s", id));

        return "project_collaborators";
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.POST)
    public String updateCollaborators(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            result.getAllErrors().forEach(System.out::println);
            return String.format("redirect:/projects/%s/collaborators", project.getId());
        }

        projectService.save(project);

        return String.format("redirect:/projects/%s", project.getId());
    }


    private Map<Role, Collaborator> mapRoles(List<Role> roles, List<Collaborator> collaborators) {
        Map<Role, Collaborator> map = new HashMap<>();
        for (Role role : roles) {
            boolean roleFound = false;
            Collaborator collaborator = null;
            for (Collaborator c : collaborators) {
                if (c.getRole() == role) {
                    roleFound = true;
                    collaborator = c;
                    break;
                }
            }
            if (roleFound) {
                map.put(role, collaborator);
            } else {
                map.put(role, null);
            }
            roleFound = false;
        }

        return map;
    }
}
