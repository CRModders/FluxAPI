buildscript {
    repositories {
        maven {
            name = "JitPack"
            url = uri("https://jitpack.io")
        }
        mavenCentral()
    }

    dependencies {
        classpath(
            group = "org.codeberg.CRModders",
            name = "cosmic-loom",
            version = "PR11-SNAPSHOT",
        )
    }
}

rootProject.name = "Flux API"

include(
    "flux-api-base",
    "flux-resource-loader-v0",
)
