package ru.geekunivercity.entity.task;

public enum TaskImportance {
    HIGH("ВЫСОКИЙ"),
    MEDIUM("СРЕДНИЙ"),
    LOW("НИЗКИЙ");

    private String name;

    private TaskImportance(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
