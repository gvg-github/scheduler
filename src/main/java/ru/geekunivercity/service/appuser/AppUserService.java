package ru.geekunivercity.service.appuser;

import ru.geekunivercity.entity.user.AppUser;

public interface AppUserService {
	void save(AppUser appUser);

	AppUser findByEmail(String email);
}
