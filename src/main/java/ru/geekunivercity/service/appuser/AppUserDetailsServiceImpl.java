package ru.geekunivercity.service.appuser;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.geekunivercity.entity.role.Role;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.repository.appuser.AppUserRepository;

import java.util.HashSet;
import java.util.Set;

@Service("userDetailsService")
public class AppUserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private AppUserRepository userRepository;

	@Override
	@Transactional(readOnly = true)
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		try {
			AppUser user = userRepository.findByEmail(email);

			Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
			for (Role role : user.getRoles()){
				grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
			}

			return new User(user.getEmail(), user.getPassword(), grantedAuthorities);
		} catch (Exception e) {
			throw new UsernameNotFoundException("User not found!");
		}
	}

}
