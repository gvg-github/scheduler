package ru.geekunivercity.entity.user;

import org.springframework.data.annotation.Transient;
import ru.geekunivercity.entity.AbstractEntity;
import ru.geekunivercity.entity.role.Role;
import ru.geekunivercity.entity.task.Task;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "AppUser")
public class AppUser extends AbstractEntity {

	@Column(name = "email", nullable = false, unique = true)
	@Email(message = "Please provide a valid e-mail")
	@NotEmpty(message = "Please provide an e-mail")
	private String email;

	@Column(name = "password")
	@Transient
	private String password;

	@Column(name = "first_name")
	@NotEmpty(message = "Please provide your first name")
	private String firstName;

	@Column(name = "last_name")
	@NotEmpty(message = "Please provide your last name")
	private String lastName;

	@Column(name = "enabled")
	private boolean enabled;

	@Column(name = "confirmation_token")
	private String confirmationToken;

	@ManyToMany
	@JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles;

	@OneToMany(mappedBy = "appUser",
					cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
	private Set<Task> tasks = new HashSet<>();

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getConfirmationToken() {
		return confirmationToken;
	}

	public void setConfirmationToken(String confirmationToken) {
		this.confirmationToken = confirmationToken;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public Set<Task> getTask() {
		return tasks;
	}

	public void setTask(Set<Task> tasks) {
		this.tasks = tasks;
	}
}
