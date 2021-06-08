package edu.ds.ms.retail.catalog2;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/category")
@Slf4j
public class CategoryController {
	@GetMapping("/all")
	public String getAllCategories() {
		
		//logging demo using lombak slf4j (defaults to using Logback implementation)
		log.trace("A TRACE Message.");
		log.debug("A DEBUG Message.");
		log.info("A INFO Message.");
		log.warn("A WARN Message.");
		log.error("A ERROR Message.");
		
		return "Logging demo. Check logs.";
	}
}
