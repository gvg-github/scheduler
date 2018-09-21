package ru.geekunivercity.entity.task;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;
import ru.geekunivercity.entity.AbstractEntity;
import ru.geekunivercity.entity.user.AppUser;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Task extends AbstractEntity implements Serializable {

    @NotNull
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date plannedStartTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date actualStartTime;

    @NotNull
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
    private Date plannedEndTime;

    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm")
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

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    private AppUser appUser;
}
