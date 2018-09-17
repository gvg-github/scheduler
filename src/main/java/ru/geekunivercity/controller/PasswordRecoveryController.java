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
import java.util.UUID;

@Controller
public class PasswordRecoveryController {

	@Autowired
	private AppUserServiceImpl userService;

	@Autowired
	private EmailService emailService;

	@RequestMapping(value = "/forgot-password", method = RequestMethod.GET)
	public ModelAndView resetPasswordRequestView() {
		ModelAndView model = new ModelAndView();
		model.setViewName("forgot-password");
		return model;
	}

	@RequestMapping(value = "/forgot-password", method = RequestMethod.POST)
	public ModelAndView resetPasswordRequest(
					ModelAndView modelAndView,
					@RequestParam("email") String email,
					HttpServletRequest request) {

		AppUser appUserExists = userService.findByEmail(email);

		if (appUserExists == null) {
			modelAndView.addObject("errorMessage", "Email не зарегистрирован");
		} else {
			modelAndView.addObject("successMessage", "Проверьте email для продолжения");
			String appUrl = request.getScheme() + "://" + request.getServerName();
			SimpleMailMessage registrationEmail = new SimpleMailMessage();
			registrationEmail.setTo(appUserExists.getEmail());
			registrationEmail.setSubject("Registration Confirmation");
			registrationEmail.setText("To reset your password, please click the link below:\n"
							+ appUrl + "/reset-password?token=" + appUserExists.getConfirmationToken());
			registrationEmail.setFrom("noreply@domain.com");
			emailService.sendEmail(registrationEmail);
		}
		modelAndView.setViewName("forgot-password");
		return modelAndView;
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.GET)
	public ModelAndView passwordResetForm(
					@RequestParam("token") String token) {
		ModelAndView modelAndView = new ModelAndView();
		AppUser appUser = userService.findByConfirmationToken(token);
		modelAndView.setViewName("reset-password");
		if (appUser == null) { // No token found in DB
			modelAndView.addObject("errorMessage", "Oops! Это неверная ссылка для восстановления.");
		} else { // Token found
			modelAndView.addObject("token", token);
		}
		return modelAndView;
	}

	@RequestMapping(value = "/reset-password", method = RequestMethod.POST)
	public ModelAndView processPasswordResetForm(
					ModelAndView modelAndView,
					@RequestParam("token") String token,
					@RequestParam("password") String password,
					BindingResult bindingResult,
					RedirectAttributes redir) {
		AppUser appUser = userService.findByConfirmationToken(token);
		if (appUser != null) {
			Zxcvbn passwordCheck = new Zxcvbn();
			Strength strength = passwordCheck.measure(password);
			if (strength.getScore() < 1) {
				bindingResult.reject("password");
				redir.addFlashAttribute("errorMessage", "Пароль слишком простой.");
				modelAndView.setViewName("redirect:/reset-password");
				return modelAndView;
			}
			// Set new password
			appUser.setPassword(password);
			appUser.setConfirmationToken(UUID.randomUUID().toString());
			userService.save(appUser);
			modelAndView.addObject("confirmationMessage", "Пароль успешно изменен");
		} else {
			modelAndView.addObject("errorMessage", "Oops! Это неверная ссылка для восстановления.");
		}
		modelAndView.setViewName("reset-password");
		return modelAndView;
	}
}
