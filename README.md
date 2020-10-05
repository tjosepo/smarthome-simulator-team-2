## smarthome-simulator

smarthome-simulator is a Java application that allows you to make simulations of smart home software by integrating to this application them as modules.

## Building locally
To build this project, make sure you have [Java SE](https://www.oracle.com/java/technologies/javase-downloads.html) installed. The project was configured with SE 13, but any Java version above 7 should work.

### Using Eclipse
Import this repository by selecting ``File > Import > Git`` and choosing ``Project from Git (with smart import)``

To import the dependencies, right-click the project and select ``Configure > Add Gradle Nature``

To run the application, right-click ``src/main/java/smarthome`` and select ``Run As > Java Application``

### Using IntelliJ IDEA
Import this repository by selecting ``File > New > Project from version control...``

To run the application, right-click ``src/main/java/smarthome/Main`` and select ``Run 'Main.main()'``

## Building dashboard
To build the dashboard, make sure you have [Node](https://nodejs.org/en/) and [npm](https://www.npmjs.com/) installed. The Node installer includes npm by default.

I recommend [VS Code](https://code.visualstudio.com/) when working on the dashboard. 

Go to ``src/main/dashboard``, and in a terminal:

```
npm install
npm run build
```

You can run the development server with:

```
npm start
```
