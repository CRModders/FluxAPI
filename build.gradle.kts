@file:Suppress("NAME_SHADOWING")

import dev.crmodders.cosmicloom.CosmicLoomPlugin
import dev.crmodders.cosmicloom.task.tasks.RunClientTask
import dev.crmodders.cosmicloom.task.tasks.RunServerTask

object Constants {
    const val GROUP = "dev.crmodders"
    const val MODID = "flux-api"
    const val VERSION = "0.8.0-alpha.3"

    const val SUBGROUP = "${GROUP}.${MODID}"

    const val VERSION_COSMIC_REACH = "0.4.4"
    const val VERSION_COSMIC_QUILT = "2.3.2"
}

plugins {
    `java-library`
    `maven-publish`
    id("cosmicloom")
}

base {
    group = Constants.GROUP
    archivesName = Constants.MODID
    version = Constants.VERSION
}

java {
    withSourcesJar()
//    withJavadocJar()

    toolchain {
        // Sets the Java version to use
        languageVersion = JavaLanguageVersion.of(17)
    }
}

subprojects {
    apply<JavaLibraryPlugin>()
    apply<CosmicLoomPlugin>()
    apply<MavenPublishPlugin>()

    base {
        group = Constants.SUBGROUP
        archivesName = name
        version = Constants.VERSION
    }
}

dependencies {
    subprojects {
        api(project)
        include(project)
    }
}

allprojects {
    loom {
        splitEnvironmentSourceSets()
    }

    sourceSets {
        val client by existing {}

        val testmod by registering {
            val main = main.get()

            compileClasspath += main.compileClasspath
            runtimeClasspath += main.runtimeClasspath
        }

        val testmodClient by registering {
            val main = main.get()
            val client by getting
            val testmod by getting

            compileClasspath += main.compileClasspath
            runtimeClasspath += main.runtimeClasspath
            compileClasspath += client.compileClasspath
            runtimeClasspath += client.runtimeClasspath

            compileClasspath += testmod.compileClasspath
            runtimeClasspath += testmod.runtimeClasspath
        }

        val test by existing {
            val testmodClient by getting

            compileClasspath += testmodClient.compileClasspath
            runtimeClasspath += testmodClient.runtimeClasspath
        }
    }

    dependencies {
        cosmicReach(loom.cosmicReachClient("alpha", Constants.VERSION_COSMIC_REACH))
        cosmicReachServer(loom.cosmicReachServer("alpha", Constants.VERSION_COSMIC_REACH))
        modImplementation(loom.cosmicQuilt(Constants.VERSION_COSMIC_QUILT))
    }

    tasks {
        val runClient by existing(RunClientTask::class)
        val runServer by existing(RunServerTask::class)

        val runTestmodClient by registering(RunClientTask::class) {
            description = "Runs the Cosmic Reach testmod client"
            isIgnoreExitValue = true
            gameJar = runClient.get().gameJar
            source = "testmodClient"
            classpath(source.map { sourceSets[it].runtimeClasspath })
        }

        val runTestmodServer by registering(RunServerTask::class) {
            description = "Runs the Cosmic Reach testmod server"
            gameJar = runServer.get().gameJar
            source = "testmod"
            classpath(source.map { sourceSets[it].runtimeClasspath })
        }

        withType<ProcessResources> {
            // Locations of where to inject the properties
            val resourceTargets = listOf(
                "quilt.mod.json"
            )

            // Left item is the name in the target, right is the variable name
            val replaceProperties = mapOf(
                "mod_group" to Constants.GROUP,
                "mod_version" to Constants.VERSION,

                // Not all versions have both client and server
                "cosmic_reach_version" to Constants.VERSION_COSMIC_REACH,
            )

            inputs.properties(replaceProperties)

            filesMatching(resourceTargets) {
                expand(replaceProperties)
            }
        }
    }

    @Suppress("UnstableApiUsage")
    testing {
        suites {
            val test by getting(JvmTestSuite::class) {
                useJUnitJupiter()
            }
        }
    }

    publishing {
        publications {
            create<MavenPublication>("maven") {
                groupId = project.group as String
                artifactId = project.base.archivesName.get()

                from(components["java"])
            }
        }
    }
}
