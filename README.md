> [!IMPORTANT]
> FluxAPI has been discontinued.
>
> It is still avaliable for legacy mods.

# Flux API

## Usage

### Adding to Project

Flux API is available on [JitPack] and can be made available by adding the
following to your `build.gradle(.kts)`, though you don't have to if you already
have the [Cosmic Loom] gradle plugin.

[Cosmic Loom]: https://codeberg.org/CRModders/cosmic-loom
[JitPack]: https://github.com/jitpack/jitpack.io

```gradle.kts
repositories {
    maven {
        name = "JitPack"
        url = uri("https://jitpack.io")
    }
}
```

After adding the maven repository, a dependency should be declared in the same
script for Gradle to include it with building.

```gradle.kts
dependencies {
    modImplementation("com.github.CRModders:FluxAPI:0.8.0")
}
```

## Building

Flux API uses the Cosmic Loom plugin, you may refer to the plugin's
documentation. Following are some example commands: 

```sh
# Creates the JARs and places it at: ./build/libs/
./gradlew build

# Runs Cosmic Reach on the development environment
./gradlew runClient
./gradlew runServer

# Ignores cached version numbers fo Gradle can download new ones that it missed
# Useful with `SNAPSHOT` dependencies from JitPack
./gradlew --refresh-dependencies
```
