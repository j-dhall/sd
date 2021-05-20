package io.educative.mediaapp;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

//@lombok.Data
@Data
@Entity
@Table(name = "playlist")
public class Playlist {
	/* obsolete for "educative.io Database Integration: Spring Data"
	private long id = 1;
	private String name = "guitar";
	private List<Song> songs = Arrays.asList(new Song());
	private Date createdOn = new Date();
	*/
	
	public Playlist() {}
	
	public Playlist(String name, Date createdOn) {
		super();
		this.name = name;
		this.createdOn = createdOn;
	}

	@GeneratedValue(strategy = GenerationType.AUTO)
	@Id
	private BigInteger id;
	
	private String name;
	
	@Column(name = "created_on")
	@JsonProperty("created_on")
	private Date createdOn;
	
	@ElementCollection(targetClass = java.util.HashSet.class)
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn
	private Collection<Song> songs;
}
