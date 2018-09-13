package ru.geekunivercity.service.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekunivercity.entity.role.Role;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.repository.role.RoleRepository;
import ru.geekunivercity.repository.appuser.AppUserRepository;

import java.util.Arrays;
import java.util.HashSet;

@Service("userService")
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository appUserRepository;

	@Autowired
	private RoleRepository roleRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	//TODO (encoder)
	@Override
	public void save(AppUser appUser) {
		//appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
		Role userRole = roleRepository.findByName("ROLE_USER");
		appUser.setRoles(new HashSet<Role>(Arrays.asList(userRole)));
		appUserRepository.save(appUser);
	}

	public AppUser findByEmail(String email) {
		return appUserRepository.findByEmail(email);
	}

	public AppUser findByConfirmationToken(String confirmationToken) {
		return appUserRepository.findByConfirmationToken(confirmationToken);
	}

}
