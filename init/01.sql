CREATE DATABASE IF NOT EXISTS `authuser`;
CREATE DATABASE IF NOT EXISTS `course`;
CREATE DATABASE IF NOT EXISTS `notification`;
GRANT ALL ON `authuser`.* TO 'root'@'%';
GRANT ALL ON `course`.* TO 'root'@'%';
GRANT ALL ON `notification`.* TO 'root'@'%';