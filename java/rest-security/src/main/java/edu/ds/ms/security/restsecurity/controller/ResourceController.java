package edu.ds.ms.security.restsecurity.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
public class ResourceController {
	@GetMapping("/hellouser")
	public String getUser() {
		log.debug("INSIDE ResourceController.getUser()");
		return "Hello User!";
	}
	
	@GetMapping("/helloadmin")
	public String getAdmin() {
		log.debug("INSIDE ResourceController.getAdmin()");
		return "Hello Admin!";
	}
}
