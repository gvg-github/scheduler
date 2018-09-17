package ru.geekunivercity.entity.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekunivercity.entity.AbstractEntity;
import ru.geekunivercity.entity.user.AppUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class TaskCategory extends AbstractEntity implements Serializable {

    @NotNull
    @Column(nullable = false)
    private String name;

    @OneToMany(mappedBy = "taskCategory", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private Set<Task> taskSet = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;

    public TaskCategory(@NotNull String name) {
        this.name = name;
    }
}
