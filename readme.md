# Flux API

### Using flux in your projects

Step 1: Add [CRModder's Maven](https://maven.crmodders.dev/) in your build.gradle at the end of your repositories tag.\
Here is an example `repositories` section
```
repositories {
	maven { url 'https://maven.crmodders.dev/releases/' }
	maven { url 'https://maven.crmodders.dev/snapshots/' }
	
	mavenCentral()
}
```

Step 2: Add Flux to your dependencies using the text below
```
implementation "dev.crmodders:fluxapi:0.6.1"
```

### Dev Commands (for contributors)
To run Cosmic Quilt in the dev env, run `gradle runQuilt`\
To run Fabric in the dev env, run `gradle runFabric`\
To build, run `gradlew build` with the jar being in `build/libs/` and ending with `-all.jar`

### Translations
The translations repo is at https://github.com/CRModders/Flux-Translations
