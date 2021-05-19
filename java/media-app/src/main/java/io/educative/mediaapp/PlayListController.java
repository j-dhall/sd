package io.educative.mediaapp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/playlist")
public class PlayListController {
	@GetMapping("/")
	public String getAllPlaylists(Model model) {
		model.addAttribute("playlists", Arrays.asList(new PlayList()));
		return "index";
	}
	
	@GetMapping("/all")
	public @ResponseBody PlayList getAllPlaylists() {
		return new PlayList();
	}
	
	@PostMapping("/new")
	public ModelAndView createPlaylist(@RequestBody PlayList playlist) {
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("playlists", playlist);
		
		return new ModelAndView("index", attributes);
	}
}
