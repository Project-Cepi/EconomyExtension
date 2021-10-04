import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    // Apply the Kotlin JVM plugin to add support for Kotlin.
    kotlin("jvm") version "1.5.30"
    id("com.github.johnrengelman.shadow") version "7.1.0"
    id("org.jetbrains.dokka") version "1.5.30"
    kotlin("plugin.serialization") version "1.4.21"
    `maven-publish`

    // Apply the application plugin to add support for building a jar
    java
}

repositories {
    // Use mavenCentral
    mavenCentral()

    maven(url = "https://jitpack.io")
    maven(url = "https://repo.spongepowered.org/maven")
    maven(url = "https://repo.minestom.com/repository/maven-public/")
    maven(url = "https://repo.velocitypowered.com/snapshots/")
}

dependencies {
    // Align versions of all Kotlin components
    compileOnly(platform("org.jetbrains.kotlin:kotlin-bom"))

    // Use the Kotlin JDK 8 standard library.
    compileOnly(kotlin("stdlib"))

    // Use the Kotlin reflect library.
    compileOnly(kotlin("reflect"))

    // Compile Minestom into project
    compileOnly("com.github.Minestom:Minestom:d7a6891153")

    // Get KStom
    compileOnly("com.github.Project-Cepi:KStom:e3ddf8f437")

    // import kotlinx serialization
    compileOnly("org.jetbrains.kotlinx:kotlinx-serialization-json:1.2.2")

    // Add Kepi
    compileOnly("com.github.Project-Cepi:Kepi:991a24276e")
}

tasks.withType<Test> {
    useJUnitPlatform()
}

configurations {
    testImplementation {
        extendsFrom(configurations.compileOnly.get())
    }
}

tasks {
    named<com.github.jengelman.gradle.plugins.shadow.tasks.ShadowJar>("shadowJar") {
        archiveBaseName.set("economy")
        mergeServiceFiles()
        minimize()
    }

    test { useJUnitPlatform() }

    build { dependsOn(shadowJar) }

}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

val compileKotlin: KotlinCompile by tasks
compileKotlin.kotlinOptions.jvmTarget = JavaVersion.VERSION_11.toString()

compileKotlin.kotlinOptions {
    freeCompilerArgs = listOf("-Xinline-classes")
}

publishing {
    publications {
        create<MavenPublication>("maven") {
            groupId = project.properties["group"] as? String?
            artifactId = project.name
            version = project.properties["version"] as? String?

            from(components["java"])
        }
    }
}