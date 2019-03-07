package com.danielturato.web;

import com.danielturato.model.Role;
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
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping("/roles")
    public String viewAllRoles(ModelMap model) {
        List<Role> roles = roleService.findAll();
        model.put("roles", roles);

        if (!model.containsAttribute("role")) {
            model.put("role", new Role());
        }

        return "roles";
    }


    @RequestMapping(value = "/roles", method = RequestMethod.POST)
    public String addRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            // Add the error message as a flash attribute
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
            // Add the role object created so the user can see their inital input after redirect
            redirectAttributes.addFlashAttribute("role", role);

            return "redirect:/roles";
        }

        roleService.save(role);

        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Role successfully added", FlashMessage.Status.SUCCESS));


        return "redirect:/roles";
    }

    @RequestMapping(value = "/roles/{roleId}")
    public String viewRole(@PathVariable Long roleId, ModelMap model) {
        Role role = roleService.findById(roleId);
        model.put("role", role);

        return "role";
    }

    @RequestMapping(value = "/roles/{roleId}", method = RequestMethod.POST)
    public String updateRole(@Valid Role role, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.role", result);
            redirectAttributes.addFlashAttribute("role", role);
            return String.format("redirect:/roles/%s/edit", role.getId());
        }

        roleService.save(role);
        redirectAttributes.addFlashAttribute("flash", new FlashMessage("Role successfully updated", FlashMessage.Status.SUCCESS));

        return "redirect:/roles";
    }

    @RequestMapping(value = "/roles/{roleId}/edit")
    public String editRole(@PathVariable Long roleId, ModelMap model) {

        if (!model.containsAttribute("role")) {
            Role role = roleService.findById(roleId);
            model.put("role", role);
        }

        model.put("action", String.format("/roles/%s", roleId));

        return "role_edit";
    }

}
