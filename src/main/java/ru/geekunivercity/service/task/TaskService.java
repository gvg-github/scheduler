package ru.geekunivercity.service.task;

import ru.geekunivercity.entity.task.Task;
import ru.geekunivercity.entity.task.TaskImportance;
import ru.geekunivercity.entity.task.TaskStatus;

import java.util.Date;
import java.util.Set;

public interface TaskService {

    void persistTask(Task task);

    void mergeTask(Task task);

    void deleteTask(Task task);

    Task findTaskById(String taskId);

    void deleteTaskById(String taskId);

    Set<Task> getTaskSetByAppUserId(String appUserId);

    void deleteTaskSetByAppUserId(String appUserId);

    Set<Task> getTaskSetByTaskCategoryId(String taskCategoryId);

    void deleteTaskSetByTaskCategoryId(String taskCategoryId);

    Set<Task> getTaskSetByTaskImportanceAndAppUserId(TaskImportance taskImportance, String appUserId);

    void deleteTaskSetByTaskImportanceAndAppUserId(TaskImportance taskImportance, String appUserId);

    Set<Task> getTaskSetByTaskStatusAndAppUserId(TaskStatus taskStatus, String appUserId);

    void deleteTaskSetByTaskStatusAndAppUserId(TaskStatus taskStatus, String appUserId);

    Set<Task> getTaskSetByPlannedStartTimeAndAppUserId(Date plannedStartTime, String appUserId);

    void deleteTaskSetByPlannedStartTimeAndAppUserId(Date plannedStartTime, String appUserId);

    Set<Task> getTaskSetByPlannedEndTimeAndAppUserId(Date plannedEndTime, String appUserId);

    void deleteTaskSetByPlannedEndTimeAndAppUserId(Date plannedEndTime, String appUserId);

    Set<Task> getTaskSetByActualStartTimeAndAppUserId(Date actualStartTime, String appUserId);

    void deleteTaskSetByActualStartTimeAndAppUserId(Date actualStartTime, String appUserId);

    Set<Task> getTaskSetByActualEndTimeAndAppUserId(Date actualEndTime, String appUserId);

    void deleteTaskSetByActualEndTimeAndAppUserId(Date actualEndTime, String appUserId);

    void setTaskName(String taskName, String taskId);

    void setTaskPlannedStartTime(Date taskPlannedStartTime, String taskId);

    void setTaskActualStartTime(Date taskActualStartTime, String taskId);

    void setTaskPlannedEndTime(Date taskPlannedEndTime, String taskId);

    void setTaskActualEndTime(Date taskActualEndTime, String taskId);

    void setTaskStatus(TaskStatus taskStatus, String taskId);

    void setTaskImportance(TaskImportance taskImportance, String taskId);

    void setTaskComment(String taskComment, String taskId);

    void setTaskCategory(String taskCategoryId, String taskId);
}