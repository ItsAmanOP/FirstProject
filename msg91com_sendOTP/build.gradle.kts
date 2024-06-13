plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    `maven-publish`
    signing
}

android {
    namespace = "io.ItsAmanOP"
    compileSdk = 34

    defaultConfig {
//        applicationId = "com.aman.sendotplibrary"
        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")
    implementation("com.google.android.material:material:1.12.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

publishing {
    publications {
        create<MavenPublication>("mavenJava") {
            artifact("$buildDir/outputs/aar/${artifactId}-release.aar")
            groupId = "io.github.itsamanop"
            artifactId = "msg91com_sendOTP"
            version = "1.0.0"

//            artifact(javadocJar.get())

            pom {
                name.set("SendOTP")
                description.set("A library for sending OTP")
                url.set("https://github.com/ItsAmanOP/sendotplibrary")

                licenses {
                    license {
                        name.set("The Apache License, Version 2.0")
                        url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                    }
                }

                developers {
                    developer {
                        id.set("amanJI")
                        name.set("Aman Gour")
                        email.set("amankumargourh@gmail.com")
                    }
                }

                scm {
                    connection.set("scm:git:github.com/ItsAmanOP/sendotplibrary")
                    developerConnection.set("scm:git:ssh://github.com/ItsAmanOP/sendotplibrary.git")
                    url.set("https://github.com/ItsAmanOP/sendotplibrary")
                }
            }
        }
    }

    repositories {
        maven {
            name = "ossrh"
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = project.findProperty("ossrhUsername") as String? ?: ""
                password = project.findProperty("ossrhPassword") as String? ?: ""
            }
        }
    }
}

signing {
    sign(publishing.publications["mavenJava"])
}