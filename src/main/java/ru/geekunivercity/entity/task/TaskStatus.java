package ru.geekunivercity.entity.task;

public enum TaskStatus {
    DONE("ВЫПОЛНЕНА"),
    IN_PROGRESS("ВЫПОЛНЯЕТСЯ"),
    AWAITS_EXECUTION("ОЖИДАЕТ ВЫПОЛНЕНИЯ"),
    OVERDUE("ПРОСРОЧЕНА");

    private String name;

    TaskStatus(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
