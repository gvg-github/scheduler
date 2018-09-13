package ru.geekunivercity.repository.appuser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.geekunivercity.entity.user.AppUser;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	AppUser findByEmail(String email);
	AppUser findByConfirmationToken(String confirmationToken);
}
