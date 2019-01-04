# Description

![alt text](https://github.com/svanloon/wizard/raw/master/archive/wizard_splash_screen.jpg "Splash Screen")

![alt text](https://github.com/svanloon/wizard/raw/master/archive/wizard_board.jpg "Game Board")

This is an online version of the card game Wizard. It is intended for network play or with a group of people. There is 
a computer player available. Also, it is intended for other developers to write bots and GUIs.

The original game was designed in 1984 by Ken Fisher, while this version was created between 2006 and 2007.


# How to download and play

* Download from [version 0.83](https://github.com/svanloon/wizard/blob/master/distribution/wizard-0.83.jar?raw=true)
Note: This is an executable jar
* Double click on the `wizard-0.83.jar`
or
* Manually run by running `java -jar wizard-0.83.jar`


# How to build and play

Need Java SDK 8 or higher to compile and play.

Create the wizard-all.jar by running
```./gradlew clean build```

Then run with
```java -jar build/libs/wizard-all.jar```

# Changes to your file system
* This program creates a file on your computer `~/wizard/userPreferences.properties`
```
LOCALE=en
DECK=Default
DISPLAY_SCORE_BOARD=false
TRICK_SUMMARY_TYPE=1
NAME=Steve
IP=127.0.0.1
```
* This file is used to store your preferences for later games 

# Network Ports

The TCP/IP ports are as follows:

* 1975 - Initial Game Connection
* 2075 - 2081 - Command Ports
* 2176 - 2182 - Event Ports
* 1356 - 1362 - Instant messaging

# Credits

* Design (original): Ken Fisher 
* Card Graphics: Wizard Cards International, Inc. http://www.wizardcards.com/
* Publisher: U.S. Games System, Inc. http://www.usgamesinc.com/
* Programmer: Steven P. Van Loon
* Special Thanks: Jennifer Royal, Jim Smitley, Scott Pierce, Rich Yarger
