package ru.geekunivercity.service.task;

import ru.geekunivercity.entity.task.TaskCategory;

import java.util.Set;

public interface TaskCategoryService {

    void persistTaskCategory(TaskCategory taskCategory);

    void mergeTaskCategory(TaskCategory taskCategory);

    void deleteTaskCategory(TaskCategory taskCategory);

    TaskCategory findTaskCategoryById(String taskCategoryId);

    void deleteTaskCategoryById(String taskCategoryId);

    Set<TaskCategory> getTaskCategorySetByAppUserId(String appUserId);

    void deleteTaskCategorySetByAppUserId(String appUserId);

    void setTaskCategoryName(String taskCategoryName, String taskCategoryId);
}