package ru.geekunivercity.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.role.Role;
import ru.geekunivercity.repository.role.RoleRepository;
import ru.geekunivercity.repository.appuser.AppUserRepository;

@Component
public class InitialDataLoader implements ApplicationListener<ContextRefreshedEvent> {

	boolean alreadySetup = false;

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Override
	@Transactional
	public void onApplicationEvent(ContextRefreshedEvent event) {

		if (alreadySetup)
			return;

		createRoleIfNotFound("ROLE_ADMIN");
		createRoleIfNotFound("ROLE_USER");

		alreadySetup = true;
	}

	@Transactional
	Role createRoleIfNotFound(String name) {

		Role role = roleRepository.findByName(name);
		if (role == null) {
			role = new Role();
			role.setName(name);
			roleRepository.save(role);
		}
		return role;
	}
}