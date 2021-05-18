package io.educative.mediaapp;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

@lombok.Data
public class PlayList {
	private long id = 1;
	private String name = "guitar";
	private List<Song> songs = Arrays.asList(new Song());
	private Date createdOn = new Date();
}
