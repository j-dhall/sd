package io.educative.mediaapp;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/playlist")
public class PlaylistController {
	
	@Autowired
	PlaylistService playlistService;
	
	@GetMapping("/")
	public String getAllPlaylists(Model model) {
		model.addAttribute("playlists", Arrays.asList(new Playlist()));
		return "index";
	}
	
	@GetMapping("/all")
	public @ResponseBody Playlist getAllPlaylists() {
		return new Playlist();
	}
	
	@GetMapping("/name/{name}")
	public @ResponseBody Playlist getPlaylist(@PathVariable String name) {
		return playlistService.getPlaylist(name).get();
	}
	
	@PostMapping("/newname")
	public ModelAndView createPlaylist(@RequestBody String name) {
		Playlist playlist = playlistService.createPlaylist(name).get();
		
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("playlists", playlist);
		
		return new ModelAndView("index", attributes);
	}
	
	@PostMapping("/new")
	public ModelAndView createPlaylist(@RequestBody Playlist pl) {
		Playlist playlist = playlistService.createNewPlaylist(pl).get();
		
		Map<String, Object> attributes = new HashMap<>();
		attributes.put("playlists", playlist);
		
		return new ModelAndView("index", attributes);
	}
}
