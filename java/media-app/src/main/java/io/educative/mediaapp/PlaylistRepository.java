package io.educative.mediaapp;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PlaylistRepository extends JpaRepository<Playlist, BigInteger> {
	public Optional<Playlist> findByName(String name);
	
	@Query("SELECT s from Song s where s.playlistId = ?1")
	public Collection<Song> getSongs(BigInteger playlistId);
}
