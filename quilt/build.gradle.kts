plugins {
    id("java-library")
    id("application")
    id("maven-publish")
    id("com.github.johnrengelman.shadow") version "8.1.1"
}

repositories {
    ivy {
        name = "Cosmic Reach"
        url = uri("https://cosmic-archive.netlify.app/")
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

    maven("https://jitpack.io")
    maven("https://maven.quiltmc.org/repository/release")
    maven("https://maven.fabricmc.net/")
    maven("https://repo.spongepowered.org/maven/")

    mavenCentral()
}

val cosmicreach: Configuration by configurations.creating {
    configurations.shadow.get().extendsFrom(this)
}

val quiltMod: Configuration by configurations.creating {
    configurations.shadow.get().extendsFrom(this)
}

dependencies {
    cosmicreach("finalforeach:cosmicreach:${project.properties["cosmic_reach_version"].toString()}")
    quiltMod("org.codeberg.CRModders:cosmic-quilt:${project.properties["cosmic_quilt_version"].toString()}")

    implementation(project(":common"))
    implementation("org.hjson:hjson:3.1.0")
    implementation("org.tinylog:tinylog:1.3.1")
    implementation("com.github.tobiasrm:tinylog-coloredconsole:1.3.1")
}

base {
    archivesName = "${properties["mod_name"]}"
    version = "${properties["mod_name"]}"
}


tasks.processResources {
    val resourceTargets = listOf("quilt.mod.json")

    val replaceProperties = mutableMapOf(
            "mod_version"     to project.version,
            "mod_group"       to project.group,
            "mod_name"        to project.name,
            "mod_id"          to project.properties["id"].toString(),
    )

    inputs.properties(replaceProperties)
    replaceProperties["project"] = project
    filesMatching(resourceTargets) {
        expand(replaceProperties)
    }
}

fun getQuiltModLocations(config: Configuration): String {
    val sb = StringBuilder();
    for (obj in config.allDependencies) {
        sb.append(File.pathSeparator + config.files(obj).first())
    }
    return sb.toString()
}

var jarFile = tasks.named<Jar>("shadowJar").flatMap { jar -> jar.archiveFile }.get().asFile
println("Mod JAR File: `$jarFile'")
val defaultArgs = listOf(
        "-Dloader.skipMcProvider=true",
        "-Dloader.gameJarPath=${cosmicreach.asPath}", // Defines path to Cosmic Reach
        "-Dloader.addMods=$jarFile${getQuiltModLocations(quiltMod)}" // Add the jar of this project
)

application {
    mainClass = "org.quiltmc.loader.impl.launch.knot.KnotClient"
    applicationDefaultJvmArgs = defaultArgs
}

tasks.runShadow.configure {
    val runDir = File("run/")
    if (!runDir.exists())
        runDir.mkdirs()
    workingDir = runDir
}

tasks.register("runClient") {
    group = "crmodders"

    dependsOn("runShadow")
}

java {
    withSourcesJar()
    // withJavadocJar() // If docs are included with the project, this line can be un-commented
}

sourceSets {
    main {
        java {
            srcDir("src/main/java")
        }
    }
}