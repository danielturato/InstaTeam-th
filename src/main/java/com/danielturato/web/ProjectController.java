package com.danielturato.web;

import com.danielturato.model.Collaborator;
import com.danielturato.model.Project;
import com.danielturato.model.Role;
import com.danielturato.service.CollaboratorServiceImpl;
import com.danielturato.service.ProjectServiceImpl;
import com.danielturato.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class ProjectController {

    @Autowired
    private ProjectServiceImpl projectService;

    @Autowired
    private RoleServiceImpl roleService;

    @Autowired
    private CollaboratorServiceImpl collaboratorService;

    @RequestMapping("/")
    public String getProjectsIndex(ModelMap model) {
        List<Project> projects = projectService.findAll();
        model.put("projects", projects);

        return "index";
    }

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

        model.put("action", "/projects");
        model.put("cancel", "/projects");
        model.put("roles", roleService.findAll());

        return "edit_project";
    }

    @RequestMapping(value = "/projects", method = RequestMethod.POST)
    public String addProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {

            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("project", project);
            return "redirect:/projects/create";
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project successfully added", FlashMessage.Status.SUCCESS));
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
        model.put("availableCollaborators", getAvailableCollaborators(p));
        model.put("action", String.format("/projects/%s/collaborators", id));
        model.put("projectCollaborators", p.getCollaborators());
        model.put("back", String.format("/projects/%s", id));

        return "project_collaborators";
    }

    @RequestMapping(value = "/projects/{id}/collaborators", method = RequestMethod.POST)
    public String updateCollaborators(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return String.format("redirect:/projects/%s/collaborators", project.getId());
        }
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project updated successfully", FlashMessage.Status.SUCCESS));
        projectService.save(project);

        return String.format("redirect:/projects/%s", project.getId());
    }

    @RequestMapping("/projects/{id}/edit")
    public String editProject(@PathVariable Long id, ModelMap model) {

        if (!model.containsAttribute("project")) {
            Project p = projectService.findById(id);
            model.put("project", p);
        }

        model.put("roles", roleService.findAll());
        model.put("action", String.format("/projects/%s", id));
        model.put("cancel", String.format("/projects/%s", id));

        return "edit_project";
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.POST)
    public String updateProject(@Valid Project project, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.project", result);
            redirectAttributes.addFlashAttribute("project", project);
            return String.format("redirect:/projects/%s/edit", project.getId());
        }

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project updated successfully", FlashMessage.Status.SUCCESS));
        validateProject(project);
        projectService.save(project);

        return String.format("redirect:/projects/%s", project.getId());
    }

    @RequestMapping("/projects/{id}/delete")
    public String deleteProject(@PathVariable Long id, ModelMap model) {
        Project p = projectService.findById(id);
        model.put("page", "project");
        model.put("name", p.getName());
        model.put("action", String.format("/projects/%s/delete", id));
        model.put("back", String.format("/projects/%s", id));

        return "delete_page";
    }

    @RequestMapping(value = "/projects/{id}/delete", method = RequestMethod.POST)
    public String removeProject(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        projectService.delete(projectService.findById(id));
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Project deleted successfully", FlashMessage.Status.SUCCESS));

        return "redirect:/projects";
    }

    private void validateProject(Project project) {
        List<Collaborator> collaborators = project.getCollaborators();
        List<Collaborator> deletedCollaborators = new ArrayList<>();

        for (Collaborator c : collaborators) {
            if (!project.getRolesNeeded().contains(c.getRole())) {
                deletedCollaborators.add(c);
            }
        }
        collaborators.removeAll(deletedCollaborators);
        project.setCollaborators(collaborators);
    }

    private List<Collaborator> getAvailableCollaborators(Project p) {
        List<Collaborator> collaborators = collaboratorService.findAll();

        for (Collaborator c : p.getCollaborators()) {
            collaborators = collaborators.stream().filter(c2 -> !c.getId().equals(c2.getId())).collect(Collectors.toList());
        }
        return collaborators;
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
