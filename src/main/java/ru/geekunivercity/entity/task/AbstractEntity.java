package ru.geekunivercity.entity.task;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.UUID;

@MappedSuperclass
class AbstractEntity  implements Serializable {

    @Id
    private String id = UUID.randomUUID().toString();
}
