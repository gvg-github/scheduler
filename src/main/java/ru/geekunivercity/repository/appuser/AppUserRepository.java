package ru.geekunivercity.repository.appuser;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import ru.geekunivercity.entity.user.AppUser;

@Repository
public interface AppUserRepository extends CrudRepository<AppUser, Long> {
	AppUser findByEmail(String email);
	AppUser findByConfirmationToken(String confirmationToken);
}
