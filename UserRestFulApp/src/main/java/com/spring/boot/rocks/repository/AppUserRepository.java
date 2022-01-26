package com.spring.boot.rocks.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.spring.boot.rocks.model.AppUser;

public interface AppUserRepository extends JpaRepository<AppUser, Long> {
	List<AppUser> findByActive(boolean active);
	List<AppUser> findByNameContaining(String name);
}
