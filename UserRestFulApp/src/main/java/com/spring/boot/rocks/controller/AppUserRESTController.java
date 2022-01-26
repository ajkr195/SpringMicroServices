package com.spring.boot.rocks.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.spring.boot.rocks.model.AppUser;
import com.spring.boot.rocks.repository.AppUserRepository;

@CrossOrigin(origins = "http://localhost:8000")
@RestController
@RequestMapping("/api")
public class AppUserRESTController {

	@Autowired
	AppUserRepository appUserRepository;

	@GetMapping("/getlist")
	public List<AppUser> getList() {
		return appUserRepository.findAll();
	}

	@GetMapping("/getuserlist")
	public ResponseEntity<List<AppUser>> getEntityList() {
		try {
			List<AppUser> appUsers = new ArrayList<AppUser>();
			appUserRepository.findAll().forEach(appUsers::add);
			if (appUsers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
			}
			return new ResponseEntity<>(appUsers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/appUsers")
	public ResponseEntity<List<AppUser>> getAllUsers(@RequestParam(required = false) String name) {
		try {
			List<AppUser> appUsers = new ArrayList<AppUser>();

			if (name == null)
				appUserRepository.findAll().forEach(appUsers::add);
			else
				appUserRepository.findByNameContaining(name).forEach(appUsers::add);

			if (appUsers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(appUsers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/appUsers/{id}")
	public ResponseEntity<AppUser> getUserById(@PathVariable("id") long id) {
		Optional<AppUser> appUserData = appUserRepository.findById(id);

		if (appUserData.isPresent()) {
			return new ResponseEntity<>(appUserData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/appUsers")
	public ResponseEntity<AppUser> createUser(@RequestBody AppUser appUser) {
		try {
			AppUser _appUser = appUserRepository
					.save(new AppUser(appUser.getName(), appUser.getEmail(), appUser.isActive()));
			return new ResponseEntity<>(_appUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping("/appUsers/{id}")
	public ResponseEntity<AppUser> updateUser(@PathVariable("id") long id, @RequestBody AppUser appUser) {
		Optional<AppUser> appUserData = appUserRepository.findById(id);

		if (appUserData.isPresent()) {
			AppUser _appUser = appUserData.get();
			_appUser.setName(appUser.getName());
			_appUser.setEmail(appUser.getEmail());
			_appUser.setActive(appUser.isActive());
			return new ResponseEntity<>(appUserRepository.save(_appUser), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/appUsers/active")
	public ResponseEntity<List<AppUser>> findByActive() {
		try {
			List<AppUser> appUsers = appUserRepository.findByActive(true);

			if (appUsers.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(appUsers, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/appUsers/{id}")
	public ResponseEntity<HttpStatus> deleteUser(@PathVariable("id") long id) {
		try {
			appUserRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping("/appUsers")
	public ResponseEntity<HttpStatus> deleteAllUsers() {
		try {
			appUserRepository.deleteAll();
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

}
