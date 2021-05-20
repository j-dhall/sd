CREATE TABLE IF NOT EXISTS playlist
(
	id			BIGINT auto_increment PRIMARY KEY,
	name		VARCHAR(250) NOT NULL,
	created_on	DATE DEFAULT sysdate
);

CREATE TABLE IF NOT EXISTS song
(
	id			BIGINT auto_increment PRIMARY KEY,
	playlist_id	BIGINT NOT NULL,
	name		VARCHAR(250) NOT NULL,
	cover_url	VARCHAR(250) NOT NULL,
	created_on	DATE DEFAULT sysdate,
	FOREIGN KEY(playlist_id) REFERENCES playlist(id) ON UPDATE CASCADE
);