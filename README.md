#Build It Bigger
An Android app with multiple flavors and libraries for delivering funny one-liners.

![Build It Bigger Preview Image](/Build_It_Bigger_Preview_Image.png?raw=true)

One of the more lighthearted projects I've done in a while, Build It Bigger is designed to be a customizable application for presenting any type of jokes or one-liners to users. In addition to building the app, I also built two libraries: a Java library called 'freshjokes' for retrieving a joke from a self-contained local plaintext file, and an Android library called 'jokedisplay' for displaying any type of joke. The focus behind the project was to modularize code functionality into re-usable, single-purpose components. 

You can completely change the tone and content of the app by introducing your own plaintext file filled with your favorite and hilarious one-liners. Besides some good humor, this application makes use of a Google Cloud Endpoints module, which can be self-hosted or deployed to the Google Cloud Platform. This allows for further extensibility in the future and currently makes for a cool proof-of-concept test scenario. Additionally, I integrated Google's Ad Mob service into the main activity layout, along with free and paid flavors of the app.

I built this project as a requirement for completing the [Gradle for Android and Java](https://www.udacity.com/course/gradle-for-android-and-java--ud867) course at [Udacity](https://www.udacity.com/). For my catalog of jokes, I went with [The Internet Chuck Norris Database's](http://www.icndb.com/) extensive collection of over 500 Chuck Norris jokes, so there is definitely plenty of content to power many future button taps.
