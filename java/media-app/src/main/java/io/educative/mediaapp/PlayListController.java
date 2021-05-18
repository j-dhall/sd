package io.educative.mediaapp;

import java.util.Arrays;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/playlist")
public class PlayListController {
	@GetMapping("/")
	public String getAllPlaylists(Model model) {
		model.addAttribute("playlists", Arrays.asList(new PlayList()));
		return "index";
	}
	
	@GetMapping("/rest")
	public @ResponseBody PlayList getAllPlaylists() {
		return new PlayList();
	}
}
