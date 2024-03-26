import java.net.URI

plugins {
    id("java")
    id("de.undercouch.download") version "5.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
}

object Properties {
    const val MOD_VERSION = "0.3.4"
    const val MOD_NAME = "FluxAPI"
    const val MOD_ID = "fluxapi"
    const val MAVEN_GROUP = "dev.crmodders.flux"
    const val COSMIC_REACH_VERSION = "0.1.14"
    const val LOADER_VERSION = "0.15.7"
    const val COSMIC_QUILT_VERSION = "0.1.5"
}

repositories {
    mavenCentral()

    maven("https://jitpack.io")
    maven("https://repo.spongepowered.org/maven/")
    maven("https://maven.fabricmc.net/")
    maven("https://maven.quiltmc.org/repository/release")

    ivy {
        name = "Cosmic Reach"
        url = URI("https://cosmic-archive.netlify.app/")
        patternLayout {
            artifact("/Cosmic Reach-[revision].jar")
        }

        metadataSources {
            artifact()
        }

        content {
            includeGroup("finalforeach")
        }
    }
}

val cosmicreach: Configuration by configurations.creating {
    configurations.shadow.get().extendsFrom(this)
}

dependencies {
    cosmicreach("finalforeach:cosmicreach:${Properties.COSMIC_REACH_VERSION}")
    shadow("net.fabricmc:sponge-mixin:0.12.5+mixin.0.8.5")

    implementation("org.hjson:hjson:3.1.0")
    implementation("org.tinylog:tinylog:1.3.1")
    implementation("com.github.tobiasrm:tinylog-coloredconsole:1.3.1")
}

tasks.test {
    useJUnitPlatform()
}