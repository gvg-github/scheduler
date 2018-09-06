package ru.geekunivercity.entity.task;

import ru.geekunivercity.entity.user.AppUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class TaskCategory extends AbstractEntity {

    @NotNull
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "taskCategory", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Task> taskSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    public TaskCategory() {
    }

    public TaskCategory(@NotNull String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(@NotNull String name) {
        this.name = name;
    }

    public Set<Task> getTaskSet() {
        return taskSet;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }
}
