package com.phoenix.pma.api.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.phoenix.pma.dao.ProjectRepository;
import com.phoenix.pma.entities.Project;

@RestController
@RequestMapping("/app-api/projects")
public class ProjectApiController {
	
	@Autowired
	ProjectRepository proRepo;

	@GetMapping
	public List<Project> EmployeesList() {
		return (List<Project>) proRepo.findAll();
	}

	@GetMapping("/{id}")
	public Project getProjectbyID(@PathVariable("id") long id) {
		return proRepo.findById(id).get();
	}

	@PostMapping(consumes = "application/json")
	@ResponseStatus(HttpStatus.CREATED)
	public Project create(@RequestBody Project proj) {
		return proRepo.save(proj);
	}

	@PutMapping(path = "/{id}", consumes = "application/json")
	@ResponseStatus(HttpStatus.OK)
	public Project update(@RequestBody Project emp) {
		return proRepo.save(emp);
	}
	
	@PatchMapping(path = "/{id}", consumes = "application/json")
	public Project partialUpdate(@PathVariable("id") long id, @RequestBody Project patchProj) {
		Project emp = proRepo.findById(id).get();
		if(patchProj.getName()!= null) {
			emp.setName(patchProj.getName());
		}
		if(patchProj.getStage() != null) {
			emp.setStage(patchProj.getStage());
		}
		return proRepo.save(emp);
	}

	@DeleteMapping("/{id}")
	public void delEmpbyID(@PathVariable("id") long id) {
		proRepo.deleteById(id);
	}
	
	@GetMapping(params = { "page", "size" })
	@ResponseStatus(HttpStatus.OK)
	public Iterable<Project> findPaginatedProjects(@RequestParam("page") int page, @RequestParam("size") int size) {
		Pageable pageAndSize = PageRequest.of(page, size);
		return proRepo.findAll(pageAndSize);
	}
	
}
