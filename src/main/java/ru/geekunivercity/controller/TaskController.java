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
import ru.geekunivercity.entity.task.Task;
import ru.geekunivercity.entity.task.TaskImportance;
import ru.geekunivercity.entity.task.TaskStatus;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.service.task.TaskCategoryServiceImpl;
import ru.geekunivercity.service.task.TaskServiceImpl;
import ru.geekunivercity.service.appuser.AppUserServiceImpl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Контроллер для работы с задачами.
 *
 * @author Valeriy Gyrievskikh
 * @since 05.09.2018.
 */

@Controller
@RequestMapping("/task")
public class TaskController {
    /**
     * Дата для подстановки по умолчанию.
     */
    private final Date defaultDate = new Date(0);

    /**
     * Сервис для работы с категориями.
     */
    @Autowired
    private TaskCategoryServiceImpl categoryService;

    /**
     * Сервис для работы с пользователями.
     */
    @Autowired
    private AppUserServiceImpl userService;

    /**
     * Сервис для работы с задачами.
     */
    @Autowired
    private TaskServiceImpl taskService;

    /**
     * Метод возвращает список задач с отбором по текущему пользователю и текущей дате.
     *
     * @param model Список задач.
     * @return Страница списка задач или страница логина, если не удалось определить текущего пользователя.
     */
    @RequestMapping(value = {"/task-list"}, method = RequestMethod.GET)
    public String tasksList(Map<String, Object> model) {

        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser user = userService.findByEmail(userEmail);
            if (user != null) {
                Date selectedDate = getDateForTask((new Date()).toString());
                Set<Task> tasks = taskService.getTaskSetByPlannedStartDateAndAppUserId(selectedDate, user.getId());
                for (Task value : tasks) {
                    if (value.getActualStartTime().getTime() == defaultDate.getTime()) {
                        value.setActualStartTime(null);
                    }
                    if (value.getActualEndTime().getTime() == defaultDate.getTime()) {
                        value.setActualEndTime(null);
                    }
                }
                model.put("taskList", tasks);
                model.put("date", selectedDate);
                return "task-list";
            }
        }
        return "login";
    }

    /**
     * Метод возвращает список задач с отбором по текущему пользователю и выбранной дате.
     *
     * @param model Список задач.
     * @return Страница списка задач, страница списка задач с текущей датой, если не удалось определить дату,
     * или страница логина, если не удалось определить текущего пользователя.
     */
    @RequestMapping(value = {"/change-date"}, method = RequestMethod.GET)
    public String tasksListByDate(@RequestParam String newdate, Map<String, Object> model) {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser user = userService.findByEmail(userEmail);
            if (user != null) {
                Date selectedDate = getDateForTask(newdate);
                model.put("taskList", taskService.getTaskSetByPlannedStartDateAndAppUserId(selectedDate, user.getId()));
                model.put("date", selectedDate);
                return "task-list";
            }
        }
        return "login";
    }

    /**
     * Метод возвращает дату в заданном формате.
     *
     * @param stringDate Строковое представление даты.
     * @return Дата.
     */
    private Date getDateForTask(String stringDate) {
        Date selectedDate = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("yyyy-MM-dd");
        try {
            selectedDate = format.parse(stringDate);
        } catch (ParseException e) {
            e.getLocalizedMessage();
        }
        return selectedDate;
    }

    /**
     * Метод возвращает email авторизованного пользователя.
     *
     * @return email или "" если не удалось определить авторизованного пользователя..
     */
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

    /**
     * Метод создает новую задачу с привязкой к текущему пользователю.
     *
     * @param model Созданная задача.
     * @return Страница редактирования задачи.
     */
    @RequestMapping(value = {"/task-create"}, method = RequestMethod.GET)
    public String taskCreate(Map<String, Object> model) {
        String userEmail = getEmailAuthUser();
        if (!userEmail.equals("")) {
            AppUser user = userService.findByEmail(userEmail);
            if (user != null) {
                Task task = new Task();
                task.setAppUser(user);
                task.setTaskStatus(TaskStatus.AWAITS_EXECUTION);
                task.setTaskImportance(TaskImportance.MEDIUM);
                task.setPlannedStartTime(new Date());
                task.setPlannedEndTime(new Date());
                task.setActualStartTime(null);
                task.setActualEndTime(null);
                task.setTaskCategory(categoryService.findTaskCategories().get(0));
                model.put("task", task);
                return "task-edit";
            }
        }
        return "redirect:/task/task-list";
    }

    /**
     * Метод вызывает страницу редактирования выбранной задачи.
     *
     * @param taskId Идентификатор задачи.
     * @param model  Выборанная задача.
     * @return Страница редактирования задачи.
     */
    @RequestMapping(value = {"/edit"}, method = RequestMethod.POST)
    public String taskEdit(@RequestParam("taskId") String taskId, Map<String, Object> model) {
        final Task task = taskService.findTaskById(taskId);
        model.put("task", task);
        return "task-edit";
    }

    /**
     * Метод сохраняет изменения задачи в открывает страницу списка задач.
     *
     * @param task Редактируемая задача.
     * @return Страница списка задач.
     */
    @RequestMapping(value = {"/task-save"}, method = RequestMethod.POST)
    public String taskSave(@ModelAttribute("task") Task task) {
        if (task.getActualStartTime() == null) {
            task.setActualStartTime(defaultDate);
        }
        if (task.getActualEndTime() == null) {
            task.setActualEndTime(defaultDate);
        }
        if (task.getTaskCategory() == null) {
            task.setTaskCategory(categoryService.findTaskCategories().get(0));
        }
        taskService.mergeTask(task);
        return "redirect:/task/task-list";
    }

    /**
     * Метод отменяет редактирование задачи.
     *
     * @return Страница списка задач.
     */
    @RequestMapping(value = {"/task-cancel"}, method = RequestMethod.POST)
    public String taskCancelSave() {
        return "redirect:/task/task-list";
    }

    /**
     * Метод удаляет выбранную задачу.
     *
     * @param taskId Идентификатор выбранной задачи.
     * @return Страница списка задач.
     */
    @RequestMapping(value = {"/delete"}, method = RequestMethod.POST)
    public String taskDelete(@RequestParam("taskId") String taskId) {
        taskService.deleteTaskById(taskId);
        return "redirect:/task/task-list";
    }

}
