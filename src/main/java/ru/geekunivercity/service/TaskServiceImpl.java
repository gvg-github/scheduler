package ru.geekunivercity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.Task;
import ru.geekunivercity.entity.TaskCategory;
import ru.geekunivercity.entity.TaskImportance;
import ru.geekunivercity.entity.TaskStatus;
import ru.geekunivercity.repository.TaskCategoryRepository;
import ru.geekunivercity.repository.TaskRepository;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    @Override
    @Transactional
    public void persistTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void mergeTask(Task task) {
        taskRepository.save(task);
    }

    @Override
    @Transactional
    public void deleteTask(Task task) {
        taskRepository.delete(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Task findTaskById(String taskId) {
        Optional<Task> opt = taskRepository.findById(taskId);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteTaskById(String taskId) {
        taskRepository.deleteById(taskId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByAppUserId(String appUserId) {
        return taskRepository.getAllByAppUserId(appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByAppUserId(String appUserId) {
        taskRepository.deleteAllByAppUserId(appUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByTaskCategoryId(String taskCategoryId) {
        return taskRepository.getAllByTaskCategoryId(taskCategoryId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByTaskCategoryId(String taskCategoryId) {
        taskRepository.getAllByTaskCategoryId(taskCategoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByTaskImportanceAndAppUserId(TaskImportance taskImportance, String appUserId) {
        return taskRepository.getAllByTaskImportanceAndAppUserId(taskImportance, appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByTaskImportanceAndAppUserId(TaskImportance taskImportance, String appUserId) {
        taskRepository.getAllByTaskImportanceAndAppUserId(taskImportance, appUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByTaskStatusAndAppUserId(TaskStatus taskStatus, String appUserId) {
        return taskRepository.getAllByTaskStatusAndAppUserId(taskStatus, appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByTaskStatusAndAppUserId(TaskStatus taskStatus, String appUserId) {
        taskRepository.deleteAllByTaskStatusAndAppUserId(taskStatus, appUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByPlannedStartTimeAndAppUserId(Date plannedStartTime, String appUserId) {
        return taskRepository.getAllByPlannedStartTimeAndAppUserId(plannedStartTime, appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByPlannedStartTimeAndAppUserId(Date plannedStartTime, String appUserId) {
        taskRepository.getAllByPlannedStartTimeAndAppUserId(plannedStartTime, appUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByPlannedEndTimeAndAppUserId(Date plannedEndTime, String appUserId) {
        return taskRepository.getAllByPlannedEndTimeAndAppUserId(plannedEndTime, appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByPlannedEndTimeAndAppUserId(Date plannedEndTime, String appUserId) {
        taskRepository.getAllByPlannedEndTimeAndAppUserId(plannedEndTime, appUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByActualStartTimeAndAppUserId(Date actualStartTime, String appUserId) {
        return taskRepository.getAllByActualStartTimeAndAppUserId(actualStartTime, appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByActualStartTimeAndAppUserId(Date actualStartTime, String appUserId) {
        taskRepository.deleteAllByActualStartTimeAndAppUserId(actualStartTime, appUserId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<Task> getTaskSetByActualEndTimeAndAppUserId(Date actualEndTime, String appUserId) {
        return taskRepository.getAllByActualEndTimeAndAppUserId(actualEndTime, appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskSetByActualEndTimeAndAppUserId(Date actualEndTime, String appUserId) {
        taskRepository.deleteAllByActualEndTimeAndAppUserId(actualEndTime, appUserId);
    }

    @Override
    @Transactional
    public void setTaskName(String taskName, String taskId) {
        taskRepository.setTaskName(taskName, taskId);
    }

    @Override
    @Transactional
    public void setTaskPlannedStartTime(Date taskPlannedStartTime, String taskId) {
        taskRepository.setTaskPlannedStartTime(taskPlannedStartTime, taskId);
    }

    @Override
    @Transactional
    public void setTaskActualStartTime(Date taskActualStartTime, String taskId) {
        taskRepository.setTaskActualStartTime(taskActualStartTime, taskId);
    }

    @Override
    @Transactional
    public void setTaskPlannedEndTime(Date taskPlannedEndTime, String taskId) {
        taskRepository.setTaskPlannedEndTime(taskPlannedEndTime, taskId);
    }

    @Override
    @Transactional
    public void setTaskActualEndTime(Date taskActualEndTime, String taskId) {
        taskRepository.setTaskActualEndTime(taskActualEndTime, taskId);
    }

    @Override
    @Transactional
    public void setTaskStatus(TaskStatus taskStatus, String taskId) {
        taskRepository.setTaskStatus(taskStatus, taskId);
    }

    @Override
    @Transactional
    public void setTaskImportance(TaskImportance taskImportance, String taskId) {
        taskRepository.setTaskImportance(taskImportance, taskId);
    }

    @Override
    @Transactional
    public void setTaskComment(String taskComment, String taskId) {
        taskRepository.setTaskComment(taskComment, taskId);
    }

    @Override
    @Transactional
    public void setTaskCategory(String taskCategoryId, String taskId) {
        Optional<TaskCategory> optionalTaskCategory = taskCategoryRepository.findById(taskCategoryId);
        if (optionalTaskCategory.isPresent()) {
            Optional<Task> optionalTask = taskRepository.findById(taskId);
            if (optionalTask.isPresent()) {
                optionalTask.get().setTaskCategory(optionalTaskCategory.get());
                taskRepository.save(optionalTask.get());
            }
        }
    }
}
