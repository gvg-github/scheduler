package ru.geekunivercity.repository.role;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.geekunivercity.entity.role.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {
	Role findByName(String name);
}
