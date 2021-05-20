package io.educative.mediaapp;

import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedNativeQuery;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

//@lombok.Data

@Data
@Entity
@Table(name = "song")
@NamedNativeQuery(name = "songsByPlaylistId", query = "SELECT id, playlist_id, name, cover_url, created_on FROM song s WHERE s.playlist_id = ?", resultClass = Song.class)
public class Song {
	/*
	private long id = 2;
	private String name = "guitar song";
	private String coverUrl = "http://youtube.com/november-rain";
	private Date createdOn = new Date();
	*/
	
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	BigInteger id;
	
	@Column(name = "playlist_id")
	@JsonProperty("playlist_id")
	BigInteger playlistId;
	
	String name;
	
	@Column(name = "cover_url")
	@JsonProperty("cover_url")
	String coverUrl;
	
	@Column(name = "created_on")
	@JsonProperty("created_on")
	String createdOn;
}
