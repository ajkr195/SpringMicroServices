package com.spring.boot.rocks.controllers;

import java.util.List;
import java.util.function.Supplier;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.spring.boot.rocks.dtos.AppUser;
import com.spring.boot.rocks.manager.AppUserManager;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;

@Controller
public class AppUserService {

	private static final Logger LOGGER = LoggerFactory.getLogger(AppUserService.class);

	@Autowired
	public AppUserManager appUserManager;

	@Autowired
	private CircuitBreaker timeCircuitBreaker;

	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request, Model model) {
		return "home";
	}

	@RequestMapping(value = "/appUsers", method = RequestMethod.GET)
	public String appUsers(HttpServletRequest request, Model model) {
		Supplier<List<AppUser>> appUsersSupplier = timeCircuitBreaker
				.decorateSupplier(() -> appUserManager.getAllAppUsersFromLibrary());

		LOGGER.info("Going to start calling the REST service with Circuit Breaker");
		List<AppUser> appUsers = null;
		for (int i = 0; i < 15; i++) {
			try {
				LOGGER.info("Retrieving appUsers from returned supplier");
				appUsers = appUsersSupplier.get();
			} catch (Exception e) {
				LOGGER.error("Could not retrieve appUsers from supplier", e);
			}
		}
		model.addAttribute("appUsers", appUsers);

		return "userslist";
	}
}
