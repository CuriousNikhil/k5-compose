import java.util.Properties

plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    `java-library`
    `maven-publish`
    signing
    id("org.jetbrains.dokka")
}
apply(plugin = "org.jlleitschuh.gradle.ktlint")

// Stub secrets to let the project sync and build without the publication values set up
ext["signing.keyId"] = null
ext["signing.password"] = null
ext["signing.secretKeyRingFile"] = null
ext["ossrhUsername"] = null
ext["ossrhPassword"] = null

// Grabbing secrets from local.properties file or from environment variables, which could be used on CI
val secretPropsFile = project.rootProject.file("local.properties")
if (secretPropsFile.exists()) {
    secretPropsFile.reader().use {
        Properties().apply {
            load(it)
        }
    }.onEach { (name, value) ->
        ext[name.toString()] = value
    }
} else {
    ext["signing.keyId"] = System.getenv("SIGNING_KEY_ID")
    ext["signing.password"] = System.getenv("SIGNING_PASSWORD")
    ext["signing.secretKeyRingFile"] = System.getenv("SIGNING_SECRET_KEY_RING_FILE")
    ext["ossrhUsername"] = System.getenv("OSSRH_USERNAME")
    ext["ossrhPassword"] = System.getenv("OSSRH_PASSWORD")
}

val dokkaHtml by tasks.getting(org.jetbrains.dokka.gradle.DokkaTask::class)
val javaDocJar: TaskProvider<Jar> by tasks.registering(Jar::class) {
    dependsOn()
    archiveClassifier.set("javadoc")
    from(dokkaHtml.outputDirectory)
}

java {
    withSourcesJar()
}

fun getExtraString(name: String) = ext[name]?.toString()

group = "me.nikhilchaudhari"
version = "1.0.1"
val pubName = "k5-compose"

publishing {
    // Configure maven central repository
    repositories {
        maven {
            name = pubName
            setUrl("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = getExtraString("ossrhUsername")
                password = getExtraString("ossrhPassword")
            }
        }
    }

    // Configure all publications
    publications.create<MavenPublication>(pubName) {
        artifactId = pubName
        artifact(javaDocJar)
        from(components["java"])

        pom {
            name.set("k5-compose")
            description.set("p5.js port for jetpack compose desktop")
            url.set("git@github.com:CuriousNikhil/k5-compose.git")
            licenses {
                license {
                    name.set("Apache 2.0")
                    url.set("https://github.com/CuriousNikhil/k5-compose/blob/main/LICENSE")
                }
            }
            developers {
                developer {
                    id.set("curiousnikhil")
                    name.set("Nikhil Chaudhari")
                    email.set("nikhyl777@gmail.com")
                }
            }
            scm {
                connection.set("scm:git:git://github.com/CuriousNikhil/k5-compose.git")
                developerConnection.set("scm:git:ssh://github.com/CuriousNikhil/k5-compose.git")
                url.set("https://github.com/CuriousNikhil/k5-compose")
            }
        }
    }
}

// Signing artifacts. Signing.* extra properties values will be used
signing {
    sign(publishing.publications[pubName])
}

repositories {
    mavenCentral()
    google()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
    dokkaHtmlPlugin("org.jetbrains.dokka:kotlin-as-java-plugin:1.5.30")
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
