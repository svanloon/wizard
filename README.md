# Description

![alt text](https://github.com/svanloon/wizard/raw/master/archive/wizard_splash_screen.jpg "Splash Screen")

![alt text](https://github.com/svanloon/wizard/raw/master/archive/wizard_board.jpg "Game Board")

This is an online version of the card game Wizard. It is intended for network play or with a group of people. There is 
a computer player available. Also, it is intended for other developers to write bots and GUIs.

The original game was designed in 1984 by Ken Fisher, while this version was created between 2006 and 2007.


# How to build and play

Need Java SDK 8 or higher to compile and play.

Create the wizard-all.jar by running
```./gradlew clean build```

Then run with
```java -jar build/libs/wizard-all.jar```

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
