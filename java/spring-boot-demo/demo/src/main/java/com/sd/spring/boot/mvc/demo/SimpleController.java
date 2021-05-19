package com.sd.spring.boot.mvc.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class SimpleController {
	@Autowired
	private BookRepository bookRepository;
	
	@Value("${spring.application.name}")
	String appName;

	@GetMapping("/")
	public String homePage(Model model) {
		model.addAttribute("appName", appName);
		return "home";// "/templates/home.html"
	}
	
	@GetMapping("/all")
	public ModelAndView findAll() {
		Iterable<Book> books = bookRepository.findAll();
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("books", books);
		return new ModelAndView("home", attributes);
	}
}
