package com.jawahar.springboot.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;
import org.springframework.stereotype.Repository;

import com.jawahar.springboot.model.Greeting;

//@Repository
@RepositoryRestResource(path = "greetings")
//public interface GreetingRepository extends JpaRepository<Greeting, Long> {
public interface GreetingRepository extends CrudRepository<Greeting, Long> {
	//@RestResource(path = "names", rel = "names")
	Collection<Greeting> findAll();
}

