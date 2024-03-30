# Flux API

### Using flux in your projects
Step 1: Add \/ in your build.gradle at the end of your repositories tag.
```
dependencyResolutionManagement {
	repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
	repositories {
		mavenCentral()
		maven { url 'https://jitpack.io' }
	}
}
```

Step 2: Add the text below into your dependencies
```
implementation "org.hjson:hjson:3.1.0" 
implementation "org.tinylog:tinylog:1.3.1"
implementation "com.github.tobiasrm:tinylog-coloredconsole:1.3.1"
implementation "com.badlogicgames.gdx:gdx-freetype:1.12.0" 
implementation "com.badlogicgames.gdx:gdx-freetype-platform:1.12.0:natives-desktop" 

implementation "dev.crmodders:FluxAPI:0.5.0"
```