package ru.geekunivercity.entity.role;


import ru.geekunivercity.entity.AbstractEntity;
import ru.geekunivercity.entity.user.AppUser;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Table(name = "Role")
public class Role extends AbstractEntity{

	private String name;

	@ManyToMany(mappedBy = "roles")
	private Set<AppUser> appUsers;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<AppUser> getAppUsers() {
		return appUsers;
	}

	public void setAppUsers(Set<AppUser> appUsers) {
		this.appUsers = appUsers;
	}
}
