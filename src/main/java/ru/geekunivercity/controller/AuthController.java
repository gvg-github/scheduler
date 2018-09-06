package ru.geekunivercity.controller;

import com.nulabinc.zxcvbn.Strength;
import com.nulabinc.zxcvbn.Zxcvbn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.geekunivercity.entity.user.AppUser;
import ru.geekunivercity.service.appuser.AppUserServiceImpl;
import ru.geekunivercity.security.SecurityService;
import ru.geekunivercity.service.email.EmailService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Map;
import java.util.UUID;

@Controller
public class AuthController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private EmailService emailService;

	// Return registration form template
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegistrationPage(ModelAndView modelAndView, AppUser appUser) {
		modelAndView.addObject("user", appUser);
		modelAndView.setViewName("register");
		return modelAndView;
	}

	// Process form input data
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ModelAndView processRegistrationForm(
					ModelAndView modelAndView,
					@Valid AppUser appUser,
					@RequestParam Map<String, String> requestParams,
					BindingResult bindingResult,
					HttpServletRequest request,
					RedirectAttributes redir) {

		// Lookup appUser in database by e-mail
		AppUser appUserExists = userService.findByEmail(appUser.getEmail());

		if (appUserExists != null) {
			modelAndView.addObject("alreadyRegisteredMessage", "Oops!  There is already a appUser registered with the email provided.");
			modelAndView.setViewName("register");
			bindingResult.reject("email");
		}

		if (bindingResult.hasErrors()) {
			modelAndView.setViewName("register");
		} else { // new appUser so we create appUser and send confirmation e-mail

			// Disable appUser until they click on confirmation link in email
			appUser.setEnabled(false);

			// Generate random 36-character string token for confirmation link
			appUser.setConfirmationToken(UUID.randomUUID().toString());

			Zxcvbn passwordCheck = new Zxcvbn();

			Strength strength = passwordCheck.measure(requestParams.get("password"));

			if (strength.getScore() < 3) {
				bindingResult.reject("password");

				redir.addFlashAttribute("errorMessage", "Your password is too weak.  Choose a stronger one.");

				modelAndView.setViewName("redirect:/register");
				return modelAndView;
			}

			// Set new password
			appUser.setPassword(requestParams.get("password"));

			userService.save(appUser);

			String appUrl = request.getScheme() + "://" + request.getServerName();

			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(appUser.getEmail());
			registrationEmail.setSubject("Registration Confirmation");
			registrationEmail.setText("To confirm your e-mail address, please click the link below:\n"
							+ appUrl + "/confirm?token=" + appUser.getConfirmationToken());
			registrationEmail.setFrom("noreply@domain.com");

			emailService.sendEmail(registrationEmail);

			modelAndView.addObject("confirmationMessage", "A confirmation e-mail has been sent to " + appUser.getEmail());
			modelAndView.setViewName("register");
		}

		return modelAndView;
	}

	// Process confirmation link
	@RequestMapping(value = "/confirm", method = RequestMethod.GET)
	public ModelAndView showConfirmationPage(ModelAndView modelAndView, @RequestParam("token") String token) {

		AppUser appUser = userService.findByConfirmationToken(token);

		if (appUser == null) { // No token found in DB
			modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { // Token found
			modelAndView.addObject("confirmationToken", appUser.getConfirmationToken());
		}

		modelAndView.setViewName("confirm");
		return modelAndView;
	}

	// Process confirmation link
	@RequestMapping(value = "/confirm", method = RequestMethod.POST)
	public ModelAndView processConfirmationForm(
					ModelAndView modelAndView,
					@RequestParam Map<String, String> requestParams) {

		modelAndView.setViewName("confirm");

		// Find the appUser associated with the reset token
		AppUser appUser = userService.findByConfirmationToken(requestParams.get("token"));

		// Set appUser to enabled
		appUser.setEnabled(true);

		// Save appUser
		userService.save(appUser);

		modelAndView.addObject("successMessage", "Your email has been confirmed!");
		return modelAndView;
	}

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.GET)
	public ModelAndView showLoginPage(ModelAndView modelAndView) {
		modelAndView.setViewName("login");
		return modelAndView;
	}

	@RequestMapping(value = {"/", "/login"}, method = RequestMethod.POST)
	public ModelAndView processLoginForm(
					ModelAndView modelAndView,
					@RequestParam Map<String, String> requestParams) {

		if (securityService.autologin(requestParams.get("email"), requestParams.get("password"))){
			modelAndView.setViewName("welcome");
		} else {
			modelAndView.setViewName("login");
			modelAndView.addObject("errorMessage", "Wrong login or password");
		}
		return modelAndView;
	}
}
