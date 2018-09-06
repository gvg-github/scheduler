package ru.geekunivercity.service.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.role.Role;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.repository.appuser.AppUserRepository;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Service
public class AppUserDetailsServiceImpl implements UserDetailsService {
	@Autowired
	private AppUserRepository appUserRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		AppUser appUser = appUserRepository.findByEmail(email);

		Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
		for (Role role : appUser.getRoles()){
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
		}

		UserDetails userDetails = (UserDetails)new org.springframework.security.core.userdetails.User(appUser.getEmail(), appUser.getPassword(), grantedAuthorities);

		return userDetails;
	}
}
