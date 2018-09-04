package ru.geekunivercity.entity;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.util.UUID;

@MappedSuperclass
class AbstractEntity {

    @Id
    private String id = UUID.randomUUID().toString();
}
