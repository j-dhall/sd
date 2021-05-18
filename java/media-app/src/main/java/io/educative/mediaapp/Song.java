package io.educative.mediaapp;

import java.util.Date;

@lombok.Data
public class Song {
	private long id = 2;
	private String name = "guitar song";
	private String coverUrl = "http://youtube.com/november-rain";
	private Date createdOn = new Date();
}
