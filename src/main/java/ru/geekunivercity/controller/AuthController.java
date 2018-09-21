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
	private EmailService emailService;

	// Return registration form template
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public ModelAndView showRegistrationPage(ModelAndView modelAndView, AppUser appUser) {
		modelAndView.addObject("user", appUser);
		modelAndView.setViewName("register");
		return modelAndView;
	}

	// Process form input data
	//TODO transfer the data in case of unsuccessful registration
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
		modelAndView.setViewName("register");
		modelAndView.addObject("firstName", requestParams.get("firstName"));
		modelAndView.addObject("lastName", requestParams.get("lastName"));
		modelAndView.addObject("email", requestParams.get("email"));

		if (appUserExists != null) {
			bindingResult.reject("email");
			redir.addFlashAttribute("errorMessage", "This email is already registered!");
		}

		if (!bindingResult.hasErrors()) {
			// new appUser so we create appUser and send confirmation e-mail
			// Disable appUser until they click on confirmation link in email
			appUser.setEnabled(false);

			// Generate random 36-character string token for confirmation link
			appUser.setConfirmationToken(UUID.randomUUID().toString());

			Zxcvbn passwordCheck = new Zxcvbn();

			Strength strength = passwordCheck.measure(requestParams.get("password"));

			if (strength.getScore() < 1) {
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
		modelAndView.setViewName("confirm");
		if (appUser == null) { // No token found in DB
			modelAndView.addObject("invalidToken", "Oops!  This is an invalid confirmation link.");
		} else { // Token found
			modelAndView.addObject("confirmationToken", appUser.getConfirmationToken());
			// Set appUser to enabled
			appUser.setEnabled(true);

			// Save appUser
			userService.save(appUser);

			modelAndView.addObject("successMessage", "Your email has been confirmed!");
		}
		return modelAndView;
	}

	@RequestMapping(value = {"/","/login"}, method = RequestMethod.GET)
	public ModelAndView login(
					@RequestParam(value = "error", required = false) String error,
					@RequestParam(value = "logout", required = false) String logout) {

		ModelAndView model = new ModelAndView();
		if (error != null) {
			model.addObject("errorMessage", "Invalid username or password!");
		}

		if (logout != null) {
			model.addObject("logoutMessage", "You've been logged out successfully.");
		}
		model.setViewName("login");

		return model;
	}

	//TODO replace with homepage
//	@RequestMapping(value = {"/","/welcome"}, method = RequestMethod.GET)
//	public ModelAndView welcome() {
//		ModelAndView model =  new ModelAndView();
//		model.setViewName("welcome");
//		return model;
//	}
}