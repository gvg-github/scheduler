package ru.geekunivercity.service.role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.role.Role;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.repository.role.RoleRepository;
import ru.geekunivercity.repository.appuser.AppUserRepository;

import java.util.Arrays;
import java.util.HashSet;

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
		createUserIfNotFound("admin@a.ru");

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

	@Transactional
	AppUser createUserIfNotFound(String userName) {

		AppUser user = appUserRepository.findByEmail(userName);
		if (user == null) {
			user = new AppUser();
			user.setEmail(userName);
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setPassword("gb1234");
			Role[] userRole = new Role[]{roleRepository.findByName("ROLE_USER"),roleRepository.findByName("ROLE_ADMIN")};
			user.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
			appUserRepository.save(user);
		}
		return user;
	}
}