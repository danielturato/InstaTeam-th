package com.danielturato.web;

import com.danielturato.model.Collaborator;
import com.danielturato.model.Role;
import com.danielturato.service.CollaboratorServiceImpl;
import com.danielturato.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class CollaboratorController {

    @Autowired
    private CollaboratorServiceImpl collaboratorService;

    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping("/collaborators")
    public String viewAllCollaborators(ModelMap model) {
        List<Collaborator> collaborators = collaboratorService.findAll();
        List<Role> roles = roleService.findAll();
        model.put("collaborators", collaborators);
        model.put("roles", roles);

        if (!model.containsAttribute("collaborator")) {
            model.put("collaborator", new Collaborator());
        }

        return "collaborators";
    }

    @RequestMapping(value = "/collaborators", method = RequestMethod.POST)
    public String addCollaborator(@Valid Collaborator c, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);
            redirectAttributes.addFlashAttribute("collaborator", c);

            return "redirect:/collaborators";
        }

        collaboratorService.save(c);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborator added successfully", FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";
    }

    @RequestMapping(value = "/collaborators/{id}")
    public String viewCollaborator(ModelMap model, @PathVariable Long id) {
        Collaborator c = collaboratorService.findById(id);
        model.put("collaborator", c);

        return "collaborator";
    }

    @RequestMapping(value = "/collaborators/{id}/edit")
    public String editCollaborator(ModelMap model, @PathVariable Long id) {

        if (!model.containsAttribute("collaborator")) {
            Collaborator c = collaboratorService.findById(id);
            model.put("collaborator", c);
        }

        model.put("roles", roleService.findAll());
        model.put("action", String.format("/collaborators/%s", id));

        return "collaborator_edit";
    }

    @RequestMapping(value = "/collaborators/{id}", method = RequestMethod.POST)
    public String updateCollaborator(@Valid Collaborator c, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.collaborator", result);
            redirectAttributes.addFlashAttribute("collaborator", c);

            return String.format("redirect:/collaborators/%s/edit", c.getId());
        }

        collaboratorService.save(c);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Collaborator saved successfully", FlashMessage.Status.SUCCESS));

        return "redirect:/collaborators";

    }

}
