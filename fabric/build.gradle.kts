import de.undercouch.gradle.tasks.download.Download
import java.net.URI

object Properties {
    const val MOD_VERSION = "0.3.4"
    const val MOD_NAME = "FluxAPI"
    const val MODID = "fluxapi"
    const val MAVEN_GROUP = "dev.crmodders.flux"
    const val COSMIC_REACH_VERSION = "0.1.14"
    const val LOADER_VERSION = "0.15.7"
}

plugins {
    id("java")
    id("de.undercouch.download") version "5.6.0"
    id("com.github.johnrengelman.shadow") version "8.1.1"
    id("maven-publish")
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


// Required Dependencies For Fabric
dependencies {
    implementation("com.google.guava:guava:33.0.0-jre")
    implementation("com.google.code.gson:gson:2.9.1")

    shadow("net.fabricmc:fabric-loader:0.15.7")
    shadow("net.fabricmc:tiny-mappings-parser:0.2.2.14")
    shadow("net.fabricmc:access-widener:2.1.0")
    shadow("net.fabricmc:sponge-mixin:0.12.5+mixin.0.8.5")

    shadow("org.ow2.asm:asm:9.6")
    shadow("org.ow2.asm:asm-util:9.6")
    shadow("org.ow2.asm:asm-tree:9.6")
    shadow("org.ow2.asm:asm-analysis:9.6")
    shadow("org.ow2.asm:asm-commons:9.6")
    shadow("io.github.llamalad7:mixinextras-fabric:0.3.5")

    cosmicreach("finalforeach:cosmicreach:${Properties.COSMIC_REACH_VERSION}")
    shadow(files("$projectDir/run/loader.jar"))
}

// Embedded | Project Dependencies
dependencies {
    implementation("org.tinylog:tinylog:1.3.1")
    implementation("com.github.tobiasrm:tinylog-coloredconsole:1.3.1")

    implementation(project(":common"))
}

base {
    archivesName = Properties.MOD_NAME
    version = Properties.MOD_VERSION
}


publishing {
    publications {
        create<MavenPublication>("maven") {
            artifactId = "flux-api"
            from(components["java"])

            pom {
                name = "Flux API"
                description = "An Extremely Capable Fabric Api Mod For Cosmic Reach"
                url = "https://crmodders.dev"

                licenses {
                    license {
                        name = " BSD-3-Clause license"
                        url = "https://opensource.org/license/bsd-3-clause"
                    }
                }

                developers {
                    developer {
                        id = "MrZombii"
                    }
                }

                scm {
                    connection = "scm:git@github.com:CRModders/FluxAPI.git"
                    developerConnection = "scm:git@github.com:CRModders/FluxAPI.git"
                    url = "https://github.com/CRModders/FluxAPI"
                }
            }

        }

    }
}

val properties = mapOf(
        "version" to Properties.MOD_VERSION,
        "loader_version" to Properties.LOADER_VERSION,
        "cosmic_reach_version" to Properties.COSMIC_REACH_VERSION,
        "mod_name" to Properties.MOD_NAME,
        "modid" to Properties.MODID,
)

tasks.processResources {
    inputs.properties(properties)

    filesMatching("fabric.mod.json") {
        expand(properties)
    }
}

tasks.register("SetupWorkEnviornment") {
    group = "crmodders"

    dependsOn("downloadCosmicReach")
    dependsOn("downloadLoader")
}

tasks.register<Download>("downloadLoader") {
    group = "crmodders"

    src("https://github.com/GalacticLoader/GalacticLoader/releases/download/${Properties.LOADER_VERSION}/GalacticLoader-${Properties.LOADER_VERSION}.jar")
    acceptAnyCertificate(true)
    dest("$projectDir/run/loader.jar")
}

tasks.register("clearCache") {
    group = "crmodders"

    if (project.file("$projectDir/build/libs/${Properties.MOD_NAME}_${Properties.MOD_VERSION}-CR_${Properties.COSMIC_REACH_VERSION}-all.jar").exists())
        project.file("$projectDir/build/libs/${Properties.MOD_NAME}_${Properties.MOD_VERSION}-CR_${Properties.COSMIC_REACH_VERSION}-all.jar").delete()
}

tasks.register("runClient") {
    group = "crmodders"

    dependsOn("clearCache")
    dependsOn("build")
    dependsOn("shadowJar")

    if (!file("$projectDir/run").exists()) file("$projectDir/run").mkdirs()

    doLast{
        var betterClasspath = listOf<File>()
        betterClasspath = betterClasspath.plus(sourceSets.main.get().compileClasspath)
        betterClasspath = betterClasspath.plus(file("$projectDir/build/libs/${Properties.MOD_NAME}-${Properties.MOD_VERSION}-all.jar"))
        System.out.println(betterClasspath)
        javaexec {
            workingDir("$projectDir/run")
            jvmArgs(
                    "-Dfabric.skipMcProvider=true",
                    "-Dfabric.development=true",
                    "-Dfabric.gameJarPath=${cosmicreach.asPath}"
            )
            classpath(betterClasspath)
            mainClass = "net.fabricmc.loader.impl.launch.knot.KnotClient"
        }
    }
}
