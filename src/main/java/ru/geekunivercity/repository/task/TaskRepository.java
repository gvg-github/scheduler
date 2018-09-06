package ru.geekunivercity.repository.task;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.task.Task;
import ru.geekunivercity.entity.task.TaskImportance;
import ru.geekunivercity.entity.task.TaskStatus;

import java.util.Date;
import java.util.Set;

@Repository
@Transactional
public interface TaskRepository extends JpaRepository<Task, String> {

    Set<Task> getAllByAppUserId(String appUserId);

    void deleteAllByAppUserId(String appUserId);

    Set<Task> getAllByTaskCategoryId(String taskCategoryId);

    void deleteAllByTaskCategoryId(String taskCategoryId);

    Set<Task> getAllByTaskImportanceAndAppUserId(TaskImportance taskImportance, String appUserId);

    void deleteAllByTaskImportanceAndAppUserId(TaskImportance taskImportance, String appUserId);

    Set<Task> getAllByTaskStatusAndAppUserId(TaskStatus taskStatus, String appUserId);

    void deleteAllByTaskStatusAndAppUserId(TaskStatus taskStatus, String appUserId);

    Set<Task> getAllByPlannedStartTimeAndAppUserId(Date plannedStartTime, String appUserId);

    void deleteAllByPlannedStartTimeAndAppUserId(Date plannedStartTime, String appUserId);

    Set<Task> getAllByPlannedEndTimeAndAppUserId(Date plannedEndTime, String appUserId);

    void deleteAllByPlannedEndTimeAndAppUserId(Date plannedEndTime, String appUserId);

    Set<Task> getAllByActualStartTimeAndAppUserId(Date actualStartTime, String appUserId);

    void deleteAllByActualStartTimeAndAppUserId(Date actualStartTime, String appUserId);

    Set<Task> getAllByActualEndTimeAndAppUserId(Date actualEndTime, String appUserId);

    void deleteAllByActualEndTimeAndAppUserId(Date actualEndTime, String appUserId);

    @Modifying
    @Query("update Task task set task.name = ?1 where task.id = ?2")
    void setTaskName(String taskName, String taskId);

    @Modifying
    @Query("update Task task set task.plannedStartTime = :taskPlannedStartTime where a.id = :taskId")
    void setTaskPlannedStartTime(@Param("taskPlannedStartTime") Date taskPlannedStartTime, @Param("taskId") String taskId);

    @Modifying
    @Query("update Task task set task.actualStartTime = ?1 where task.id = ?2")
    void setTaskActualStartTime(Date taskActualStartTime, String taskId);

    @Modifying
    @Query("update Task task set task.plannedEndTime = ?1 where task.id = ?2")
    void setTaskPlannedEndTime(Date taskPlannedEndTime, String taskId);

    @Modifying
    @Query("update Task task set task.actualEndTime = ?1 where task.id = ?2")
    void setTaskActualEndTime(Date taskActualEndTime, String taskId);

    @Modifying
    @Query("update Task task set task.taskStatus = ?1 where task.id = ?2")
    void setTaskStatus(TaskStatus taskStatus, String taskId);

    @Modifying
    @Query("update Task task set task.taskImportance = ?1 where task.id = ?2")
    void setTaskImportance(TaskImportance taskImportance, String taskId);

    @Modifying
    @Query("update Task task set task.taskComment = ?1 where task.id = ?2")
    void setTaskComment(String taskComment, String taskId);
}