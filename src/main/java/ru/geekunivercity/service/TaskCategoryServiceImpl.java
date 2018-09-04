package ru.geekunivercity.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.TaskCategory;
import ru.geekunivercity.repository.TaskCategoryRepository;

import java.util.Optional;
import java.util.Set;

@Service
public class TaskCategoryServiceImpl implements TaskCategoryService {

    @Autowired
    private TaskCategoryRepository taskCategoryRepository;

    @Override
    @Transactional
    public void persistTaskCategory(TaskCategory taskCategory) {
        taskCategoryRepository.save(taskCategory);
    }

    @Override
    @Transactional
    public void mergeTaskCategory(TaskCategory taskCategory) {
        taskCategoryRepository.save(taskCategory);
    }

    @Override
    @Transactional
    public void deleteTaskCategory(TaskCategory taskCategory) {
        taskCategoryRepository.delete(taskCategory);
    }

    @Override
    @Transactional(readOnly = true)
    public TaskCategory findTaskCategoryById(String taskCategoryId) {
        Optional<TaskCategory> opt = taskCategoryRepository.findById(taskCategoryId);
        if (opt.isPresent()) {
            return opt.get();
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteTaskCategoryById(String taskCategoryId) {
        taskCategoryRepository.deleteById(taskCategoryId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<TaskCategory> getTaskCategorySetByAppUserId(String appUserId) {
        return taskCategoryRepository.getAllByAppUserId(appUserId);
    }

    @Override
    @Transactional
    public void deleteTaskCategorySetByAppUserId(String appUserId) {
        taskCategoryRepository.deleteAllByAppUserId(appUserId);
    }

    @Override
    @Transactional
    public void setTaskCategoryName(String taskCategoryName, String taskCategoryId) {
        taskCategoryRepository.setTaskCategoryName(taskCategoryName, taskCategoryId);
    }
}
