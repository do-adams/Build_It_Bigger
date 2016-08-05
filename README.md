#Build It Bigger
An Android app with multiple flavors and libraries for delivering funny one-liners.

![Build It Bigger Preview Image](/Build_It_Bigger_Preview_Image.png?raw=true)

One of the more lighthearted projects I've done in a while, Build It Bigger is designed to be a customizable application for presenting any type of jokes or one-liners to users. In addition to building the app, I also built two libraries: a Java library called 'freshjokes' for retrieving a joke from a self-contained local plaintext file, and an Android library called 'jokedisplay' for displaying any type of joke. The focus behind the project was to modularize code functionality into re-usable, single-purpose components, all the while building something "bigger" in the process. 

You can completely change the tone and content of the app by introducing your own plaintext file filled with your favorite and hilarious one-liners. Besides some good humor, this application makes use of a Google Cloud Endpoints module, which can be self-hosted or deployed to the Google Cloud Platform. This allows for further extensibility in the future and currently makes for a cool proof-of-concept scenario. 

Additionally, I integrated Google's Ad Mob service into the main activity layout, along with free and paid flavors of the app that either do or do not make usage of the ads. Along with these, I produced a unit test for making sure the Google Cloud Endpoints module is working and returning jokes properly when activated.  

I built this project during August 2016 as a requirement for completing the [Gradle for Android and Java](https://www.udacity.com/course/gradle-for-android-and-java--ud867) course at [Udacity](https://www.udacity.com/). For my catalog of jokes, I went with [The Internet Chuck Norris Database's](http://www.icndb.com/) extensive collection of over five-hundred Chuck Norris jokes, so there is definitely plenty of content to power many greasy button taps.

#Getting Started

To begin running this project, you will definitely want to follow the steps in Google's Ad Mob [documentation](https://firebase.google.com/docs/admob/android/quick-start). 

Firstly, you will need three different 'google-services.json' files for the main, free, and paid versions of the app (all of which have different package names), which must be placed in the root of the app, free, and paid directories.

Secondly, you will need your own app and banner ad unit ids for configuring the ad in the main activity. They are located in the 'strings.xml' resource file. When you obtain these make sure to get your unique test device id and use it to overwrite the values used in the code.

Finally, you will want to make sure you run the backend module configuration for the Google Cloud Endpoints functionality. For best results, it should be used in conjunction with the Android emulator and not a test device. More details are available in the code and [here](https://github.com/GoogleCloudPlatform/gradle-appengine-templates/tree/master/HelloEndpoints).
