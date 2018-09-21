package ru.geekunivercity.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import ru.geekunivercity.entity.task.TaskCategory;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.service.appuser.AppUserService;
import ru.geekunivercity.service.task.TaskCategoryService;

import java.util.Map;

@Controller
@RequestMapping("/task-category")
public class TaskCategoryController {

    private AppUserService appUserService;

    private TaskCategoryService taskCategoryService;

    @Autowired
    public TaskCategoryController(AppUserService appUserService, TaskCategoryService taskCategoryService) {
        this.appUserService = appUserService;
        this.taskCategoryService = taskCategoryService;
    }

    @RequestMapping(value = {"/task-category-set"}, method = RequestMethod.GET)
    public String getTaskCategorySet(Map<String, Object> model) {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser appUser = appUserService.findByEmail(userEmail);
            if (appUser != null) {
                model.put("taskCategorySet", taskCategoryService.getTaskCategorySetByAppUserId(appUser.getId()));
                return "task-category-set";
            }
        }
        return "login";
    }

    @RequestMapping(value = {"/task-category-create"}, method = RequestMethod.GET)
    public String createTaskCategory(Map<String, Object> model) {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser appUser = appUserService.findByEmail(userEmail);
            if (appUser != null) {
                TaskCategory taskCategory = new TaskCategory();
                taskCategory.setAppUser(appUser);
                model.put("taskCategory", taskCategory);
                return "task-category-edit";
            }
        }
        return "redirect:/task-category/task-category-set";
    }

    @RequestMapping(value = {"/task-category-edit"}, method = RequestMethod.POST)
    public String editTaskCategory(@RequestParam("taskCategoryId") String taskCategoryId, Map<String, Object> model) {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser appUser = appUserService.findByEmail(userEmail);
            if (appUser != null) {
                TaskCategory taskCategory = taskCategoryService.findTaskCategoryById(taskCategoryId);
                model.put("taskCategory", taskCategory);
                return "task-category-edit";
            }
        }
        return "login";
    }

    @RequestMapping(value = {"/task-category-save"}, method = RequestMethod.POST)
    public String saveTaskCategory(@ModelAttribute("taskCategory") TaskCategory taskCategory) {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser appUser = appUserService.findByEmail(userEmail);
            if (appUser != null) {
                taskCategoryService.mergeTaskCategory(taskCategory);
                return "redirect:/task-category/task-category-set";
            }
        }
        return "login";
    }

    @RequestMapping(value = {"/task-category-edit-cancel"}, method = RequestMethod.POST)
    public String cancelTaskCategoryEditing() {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser appUser = appUserService.findByEmail(userEmail);
            if (appUser != null) {
                return "redirect:/task-category/task-category-set";
            }
        }
        return "login";
    }

    @RequestMapping(value = {"/task-category-delete"}, method = RequestMethod.POST)
    public String taskDelete(@RequestParam("taskCategoryId") String taskCategoryId) {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser appUser = appUserService.findByEmail(userEmail);
            if (appUser != null) {
                taskCategoryService.deleteTaskCategoryById(taskCategoryId);
                return "redirect:/task-category/task-category-set";
            }
        }
        return "login";
    }

    private String getEmailAuthUser() {
        String userEmail = "";
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            Object userDetails = authentication.getPrincipal();
            if (userDetails instanceof UserDetails) {
                userEmail = ((UserDetails) userDetails).getUsername();
            }
        }
        return userEmail;
    }
}
