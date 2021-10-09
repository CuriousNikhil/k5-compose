plugins {
    kotlin("jvm")
    id("org.jetbrains.compose")
    id("maven-publish")
    id("signing")
}
apply(plugin = "org.jlleitschuh.gradle.ktlint")

group = "me.nikhilchaudhari"
version = "1.0.0-alpha01"

java {
    withJavadocJar()
    withSourcesJar()
}

val pubName = "k5-compose"

publishing {
    publications {
        create<MavenPublication>(pubName) {
            artifactId = "k5-compose"
            from(components.findByName("java"))

            pom {
                name.set("k5-compose")
                description.set("p5.js port for jetpack compose desktop")
                url.set("https://github.com/CuriousNikhil/simplepoller")
                licenses {
                    license {
                        name.set("Apache 2.0")
                        url.set("https://github.com/CuriousNikhil/simplepoller/blob/main/LICENSE")
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
                    connection.set("scm:git:git://github.com/CuriousNikhil/simplepoller.git")
                    developerConnection.set("scm:git:ssh://github.com/CuriousNikhil/simplepoller.git")
                    url.set("https://github.com/CuriousNikhil/simplepoller")
                }
            }
        }
    }
    repositories {
        maven {

            credentials {
                username = project.properties["OSSRH_USERNAME"] as String
                password = project.properties["OSSRH_PASSWORD"] as String
            }

            name = pubName
            url = uri("https://s01.oss.sonatype.org/service/local/staging/deploy/maven2/")
        }
    }
}

signing {
    sign(publishing.publications.getByName(pubName))
}

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
}

dependencies {
    implementation(compose.desktop.currentOs)
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>() {
    kotlinOptions.jvmTarget = "11"
}
