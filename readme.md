# Flux API

### Using flux in your projects

Step 1: Add [Jitpack](https://docs.jitpack.io/) in your build.gradle at the end of your repositories tag.\
Here is an example `repositories` section
```
repositories {
	maven { url 'https://jitpack.io' }
	
	mavenCentral()
}
```

Step 2: Add Flux to your dependencies using the text below
```
implementation "dev.crmodders:FluxAPI:0.5.4"
```

### Dev Commands (for contributors)
To run Cosmic Quilt in the dev env, run `gradle runQuilt`\
To run Fabric in the dev env, run `gradle runFabric`\
To build, run `gradlew build` with the jar being in `build/libs/` and ending with `-all.jar`
