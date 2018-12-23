package com.danielturato.web;

import com.danielturato.model.Role;
import com.danielturato.service.RoleServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

@Controller
public class RoleController {

    @Autowired
    private RoleServiceImpl roleService;

    @RequestMapping("/roles")
    public String viewAllRoles(ModelMap model) {
        Role r = new Role();
        r.setName("test");
        roleService.save(r);
        List<Role> roles = roleService.findAll();
        model.put("roles", roles);

        if (!model.containsAttribute("role")) {
            model.put("role", new Role());
        }

        return "roles";
    }


//    @RequestMapping(value = "/roles", method = RequestMethod.POST)
//    public String addRole(Role role) {
//
//        roleService.save(role);
//
//
//        return "redirect:/projects";
//    }




}
