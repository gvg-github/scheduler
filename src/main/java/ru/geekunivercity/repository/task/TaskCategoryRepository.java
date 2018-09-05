package ru.geekunivercity.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.task.TaskCategory;

import java.util.Set;

@Repository
@Transactional
public interface TaskCategoryRepository extends JpaRepository<TaskCategory, String> {

    Set<TaskCategory> getAllByAppUserId(String appUserId);

    void deleteAllByAppUserId(String appUserId);

    @Modifying
    @Query("update TaskCategory taskCategory set taskCategory.name = ?1 where taskCategory.id = ?2")
    void setTaskCategoryName(String taskCategoryName, String taskCategoryId);
}