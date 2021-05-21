package io.educative.mediaapp;

public class PlaylistNotFoundException extends RuntimeException {

	public PlaylistNotFoundException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PlaylistNotFoundException(String name) {
		super(String.format("PLAYLIST %s NOT FOUND.", name));
		// TODO Auto-generated constructor stub
	}

}
