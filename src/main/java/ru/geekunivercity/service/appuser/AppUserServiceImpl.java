package ru.geekunivercity.service.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.repository.role.RoleRepository;
import ru.geekunivercity.repository.appuser.AppUserRepository;

import java.util.HashSet;

@Service("userService")
public class AppUserServiceImpl implements AppUserService {

	@Autowired
	private AppUserRepository appUserRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	@Override
	public void save(AppUser appUser) {
		appUser.setPassword(bCryptPasswordEncoder.encode(appUser.getPassword()));
		appUser.setRoles(new HashSet<>(roleRepository.findAll()));
		appUserRepository.save(appUser);
	}

	public AppUser findByEmail(String email) {
		return appUserRepository.findByEmail(email);
	}

	public AppUser findByConfirmationToken(String confirmationToken) {
		return appUserRepository.findByConfirmationToken(confirmationToken);
	}

}
