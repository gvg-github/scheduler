package ru.geekunivercity.entity.task;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity
public class Task extends AbstractEntity {

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date plannedStartTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualStartTime;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date plannedEndTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date actualEndTime;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskStatus taskStatus;

    @NotNull
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TaskImportance taskImportance;

    @Column(columnDefinition = "TEXT")
    private String taskComment;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private TaskCategory taskCategory;

//    @NotNull
//    @ManyToOne(fetch = FetchType.LAZY)
//    private AppUser appUser;

    public Task() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getPlannedStartTime() {
        return plannedStartTime;
    }

    public void setPlannedStartTime(Date plannedStartTime) {
        this.plannedStartTime = plannedStartTime;
    }

    public Date getActualStartTime() {
        return actualStartTime;
    }

    public void setActualStartTime(Date actualStartTime) {
        this.actualStartTime = actualStartTime;
    }

    public Date getPlannedEndTime() {
        return plannedEndTime;
    }

    public void setPlannedEndTime(Date plannedEndTime) {
        this.plannedEndTime = plannedEndTime;
    }

    public Date getActualEndTime() {
        return actualEndTime;
    }

    public void setActualEndTime(Date actualEndTime) {
        this.actualEndTime = actualEndTime;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public TaskImportance getTaskImportance() {
        return taskImportance;
    }

    public void setTaskImportance(TaskImportance taskImportance) {
        this.taskImportance = taskImportance;
    }

    public TaskCategory getTaskCategory() {
        return taskCategory;
    }

    public void setTaskCategory(TaskCategory taskCategory) {
        this.taskCategory = taskCategory;
    }

    public String getTaskComment() {
        return taskComment;
    }

    public void setTaskComment(String taskComment) {
        this.taskComment = taskComment;
    }

//    public AppUser getAppUser() {
//        return appUser;
//    }

//    public void setAppUser(AppUser appUser) {
//        this.appUser = appUser;
//    }
}
