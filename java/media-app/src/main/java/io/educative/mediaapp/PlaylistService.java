package io.educative.mediaapp;

import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaylistService {
	@Autowired
	private EntityManager entityManager;
	@Autowired
	private PlaylistRepository playlistRepo;
	
	public Iterable<Playlist> getAllPlaylists() {
		return playlistRepo.findAll();
	}
	
	public Optional<Playlist> getPlaylist(String name) {
		Optional<Playlist> playlist = playlistRepo.findByName(name);
		if(playlist.isEmpty()) {
			throw new PlaylistNotFoundException(name);
		}
		return playlist;
	}
	
	public void getSongs(BigInteger playlistId) {
		List<Song> songs = entityManager.createNamedQuery("songsByPlaylistId").setParameter(1, playlistId).getResultList();
		System.out.println(songs);
	}
	
	public Optional<Playlist> createPlaylist(String name) {
		Playlist pl = new Playlist(name, new Date());
		return Optional.of(playlistRepo.save(pl));
	}
	
	public Optional<Playlist> createNewPlaylist(Playlist pl) {
		return Optional.of(playlistRepo.save(pl));
	}
	
}
