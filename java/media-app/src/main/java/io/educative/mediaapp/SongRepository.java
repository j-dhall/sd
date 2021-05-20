package io.educative.mediaapp;

import java.math.BigInteger;
import java.util.Collection;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface SongRepository extends JpaRepository<Song, BigInteger> {
	public Optional<Song> findByName(String name);
	Collection<Song> findByPlaylistId(BigInteger playlistId);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("DELETE FROM Song s WHERE s.playlistId = ?1")
	public void deleteByPlaylistId(BigInteger playlistId);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("DELETE from Song s WHERE s.playlistId = ?1 and s.id = ?2")
	public void delete(BigInteger playlistId, BigInteger songId);
	@Transactional
	@Modifying(clearAutomatically = true)
	@Query("UPDATE Song s SET s.playlistId = ?2 WHERE s.id = ?1")
	public int updatePlaylist(BigInteger songId, BigInteger playlistId);
}
