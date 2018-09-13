package ru.geekunivercity.entity.role;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.geekunivercity.entity.AbstractEntity;
import ru.geekunivercity.entity.user.AppUser;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Role")
public class Role extends AbstractEntity {

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Set<AppUser> appUsers;
}
