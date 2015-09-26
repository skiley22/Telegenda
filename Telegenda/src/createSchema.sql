CREATE TABLE Keywords
(
	keywordId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	calendarId VARCHAR(255) NOT NULL,
	keywordName VARCHAR(255) NOT NULL
);

CREATE TABLE Blacklist
(
	blacklistKeywordId INT NOT NULL AUTO_INCREMENT PRIMARY KEY,
	blacklistKeyword VARCHAR(255) NOT NULL,
	savedKeywordId INT NOT NULL 
);

ALTER TABLE Blacklist
ADD FOREIGN KEY (savedKeywordId)
REFERENCES Keywords(keywordId);