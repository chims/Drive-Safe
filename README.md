Drive-Safe
==========

README.md file for JUSTdrive mobile app contains the following content: Overview of the project; Required Android Permissions and why the app needs them; Required Web Services, e.g. GIS, Google, etc., and why the app needs them; Required, built-in, features/devices: GPS, Camera, Gallery, Contacts, SMS, etc.; Testing procedures (emulators, device tests, unit tests, etc.); Screenshots; Known bugs, limitations, and problems; Set of features to be implemented or next steps; and License and contributors.

Overview of the Project
=======================

JUSTdrive is a native android mobile app aimed at promoting safe driving among teenage and young adult drivers by persuading such a target group to abstain from distracted driving occasioned by use of mobile phones.  Currently, JUSTdrive addresses the primary problem of texting while driving, and physical access of mobile phone devices to answer incoming voice calls.  The app monitors incoming SMS text messages and voice calls, and generates voice notifications that alert drivers to such communication in-streams.  Drivers are, therefore, up-to-date while on the road, and concentrate on what drivers do; JUSTdrive. Furthermore, JUSTdrive transforms a mobile phone into a device that promotes safe driving by alerting drivers whenever they drive over or under the speed limit; a feature currently available in Southern California only.

Required Android Permissions
============================

JUSTdrive requires the following android permissions which are statically declared in order to allow the app explicitly share resources and data with other apps or services for the purpose of generating necessary voice notifications:
•  RECEIVE_SMS: Allows JUSTdrive to monitor incoming SMS text messages in order to generate voice notifications that alert a driver to incoming text messages 
•	READ_PHONE_STATE: Allows JUSTdrive read-only access to phone state in order to monitor incoming calls in order to generate voice notifications that alert a driver to incoming calls
•	ACCESS_FINE_LOCATION: Allows JUSTdrive to access precise location, based on GPS location source, in order to determine a driver’s  Latitude, Longitude and current speed data
•	INTERNET: Allows JUSTdrive to open network sockets that enable the app to access a web service.
 
Required Web Services
=====================

JUSTdrive mobile app requires access to ArcGIS REST Services directory web service.  At a predefined minTime interval, the app repeatedly queries the said web service through a JSON object in order to retrieve the speed limit of the driver’s current street whenever the app is activated.  Note that the said web service currently contains a database of speed limits for Southern California roads only – in future, the web service may be extended to cover all USA roads.
   
Required, Built-in Features or Devices
======================================

JUSTdrive mobile app requires the following built-in features and/or devices in order to generate necessary voice notifications that alert the user:
•	Text To Speech Engine
•	GPS (location and speed)
•	Messaging (SMS)
•	Voice call (phone)
•	Audio Manager (volume and ringer control)

Testing Procedures
==================

Initially, JUSTdrive’s basic functionality was tested under Eclipse SDK version 4.2.2 using an Android Virtual device (AVD) Emulator running on android Jelly Bean version 4.2.2 platform.  Thereafter, thorough device tests were carried out on a Samsung Galaxy S4 (T-Mobile) Black Mist model SGH-M919 running on Android Jelly Bean version 4.2.2 platform.  Finally, unit testing was performed through exhaustive Activity testing process for the following classes and/or key functions:
•	GPS Location and Speed Tracking, and generation of subsequent voice notifications
•	Monitoring of incoming SMS, and generation of subsequent voice notifications
•	Monitoring of incoming voice calls, and generation of subsequent voice notifications
•	Device volume and ringer control

Screenshots
===========

Due to the nature and design goals of JUSTdrive, the app was developed to run as a background service with little or no user interaction.  Below is a screenshot of JUSTdrive mobile app’s single screen designed with the sole aim of allowing a driver to manually enable or disenable the app at the start or end of a driving session.  Also displayed below is a screenshot of a known problem; JUSTdrive app stops running whenever a user makes an outgoing voice call.  Such a scenario would arise in the event that a driver receives a voice notification for an important incoming voice call, and thereafter pulls over at a nearby gas station in order to return the call.
	 	 
	Fig.1:  JUSTdrive main screen upon launching
app with a Enable/Disenable toggle button 	Fig. 2: JUSTdrive TOAST message prompting driver to turn on GPS immediately after Enabling the app


	
 	 
	Fig. 3: JUSTdrive TOAST message prompting driver
 to turn OFF GPS soon after Disabling the app
	Fig. 4: JUSTdrive problem in the event that a 
driver pulls over and makes an outgoing call



Known Bugs, Limitations, and Known Problems
===========================================

The majority of issues identified during the testing phase were associated with the voice call feature.  There were very few issues related to the GPS and SMS features.  The following bugs, limitations,  and known problems were identified during user acceptance testing for JUSTdrive mobile app:
Item	Category	Issue Type	Description
1	GPS	Limitation	Whenever JUSTdrive app is enabled, user has to manually turn ON GPS ON, else tracking is not performed
1	All Notifications	Bug	Notifications are being generated multiple times for a single message e.g. twice, thrice, etc.
2	Voice call / SMS	Limitation	Generated voice notifications read-out incoming number even if the number is linked to a name in Contacts 
3	Voice call	Limitation	Albeit in silent mode, incoming voice calls unnecessarily take up active screen for the duration of the ringing-time
4	Voice call	Bug	Voice call notifications are NOT being generated once incoming calls are registered
5	SMS	Bug	Only last SMS added to queue while JUSTdrive runs in background is retained i.e. other SMS’es are flushed out 
6	Voice call	Problem	Albeit not an ideal scenario, JUSTdrive STOPS running when an outgoing call is made – see screen shot above


Set of Features to be Implemented or Next Step
==============================================

In order to take JUSTdrive project to the next level, the following features need to be implemented to resolve current bugs, limitations, and known problems.  Furthermore, additional features need to be incorporated in order to enhance the functionality of JUSTdrive by expanding the range of monitored communication in-streams.  The set of recommended features listed below will not only enrich the user experience, but will also enhance driving safety for teenage and young adult drivers.
Item	Category	Feature Type	Description
1	Email	Enhancement
 (if possible)	Monitor incoming emails and  generate voice notifications accordingly
2	Voicemail	Enhancement	Monitor incoming voicemails and  generate voice notifications accordingly
3	Reminder	Enhancement	Monitor alarm /reminders and  generate voice notifications accordingly
4	Settings	Enhancement (UI)	Allow users to pre-select communication in-streams to monitor based on personal preferences
5	Settings	Enhancement (UI)	Change the current default voice used for voice notifications to a more natural-sounding voice 
6	Report	Enhancement (UI)	Allow users to list-view notification history logs  based on JUSTdrive session(s) by date or by type 
7	GPS	Limitation-Fix	Automatically activate or deactivate GPS at the start or end of JUSTdrive session respectively
8	Voice call / SMS	Limitation Fix	When generating voice notifications, check if incoming number exists in Contacts, if TRUE, read-out name 
9	Voice call	Limitation-Fix	Automatically  hang-up or route incoming voice call to voicemail and generate voice notification accordingly
10	Voice call	Bug-Fix	Ensure ALL voice call notifications are being read-out to the user once incoming calls are registered
11	SMS	Bug-Fix	Retain all SMS messages added to queue while JUSTdrive is running in background i.e. not on active screen 
12	All Notifications	Bug-Fix	Ensure that notifications, for a single message, are read-out twice at most in case driver missed out on first one
13	Voice call	Problem-Fix	Allow JUSTdrive to run in background without STOPPING, if a driver pulls up at gas station to make a quick call 


License and Contributors
========================

In order to promote sustainable and collaborative development of JUSTdrive project, the app’s source code is currently being made available and licensed with full rights to any persons interested to study, modify, and distribute the app at no cost.  In the event that key changes are made to significantly enhance the current functionality of JUSTdrive, voluntary feedback would be much appreciated.
Contributors to JUSTdrive mobile app project are: Yousef Abed, PhD candidate, School of Information System and Technology, Claremont Graduate University; Abdoullah Murad, PhD candidate, School of Information Systems and Technology, Claremont Graduate University; Dr. Brian Hilton, core faculty liaison, IST380B - Mobile Applications, and Director, Advanced Geographic Information Systems Lab (A-GIS), Claremont Graduate University; and Chims Jere, MHIM student, Claremont Graduate University.
All communication related to JUSTdrive project must be directed to: chims.jere@cgu.edu.    
