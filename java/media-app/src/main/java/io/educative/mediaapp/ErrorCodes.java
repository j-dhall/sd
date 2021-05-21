package io.educative.mediaapp;

enum ErrorCodes {
	PLAYLIST_NOT_FOUND(1001),
	SONG_NOT_FOUND(1002);
	
	ErrorCodes(int code) {
		this.code = code;
	}

	public int code() {
		return this.code;
	}

	private int code;
}
