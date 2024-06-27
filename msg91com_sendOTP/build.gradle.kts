plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.gradleup.nmcp").version("0.0.8")
    id("maven-publish")
    id("signing")
}

//val publishingVersion: String? = System.getenv("VERSION_NAME")
val sonaTypeUseName: String? = System.getenv("SONATYPE_USER_NAME")
val sonaTypePassword: String? = System.getenv("SONATYPE_PASSWORD")
val keyId: String? = System.getenv("GPG_SIGNING_KEY_ID")
val signingKey: String? = System.getenv("GPG_SIGNING_ARMED_KEY")
val password: String? = System.getenv("GPG_SIGNING_PASSPHRASE_PASSWORD")

android {
    namespace = "io.ItsAmanOP"
    compileSdk = 34
    defaultConfig {
//        applicationId = "com.aman.sendotplibrary"
        minSdk = 24
//        targetSdk = 34
//        versionCode = 1
//        versionName = "1.0"
//        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}
dependencies {
//    implementation("androidx.core:core-ktx:1.13.1")
//    implementation("androidx.appcompat:appcompat:1.7.0")
//    implementation("com.google.android.material:material:1.12.0")
//    testImplementation("junit:junit:4.13.2")
//    androidTestImplementation("androidx.test.ext:junit:1.1.5")
//    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

group = "io.github.itsamanop"
version = System.getenv("VERSION_NAME")
val artifactName = "SendOTP"
val artifactDescription = "A description of my library"
val artifactUrl = "https://github.com/ItsAmanOP/SendOTP"

afterEvaluate {
    publishing {
        publications {
            create<MavenPublication>("release") {
                from(components["release"])
                // Add any additional configuration for your publication here

                groupId = group.toString()
                artifactId = artifactName
                version = version

                pom {
                    name.set(artifactName)
                    description.set(artifactDescription)
                    url.set(artifactUrl)

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("http://www.apache.org/licenses/LICENSE-2.0.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("ItsAmanOP")
                            name.set("Aman Karpentar")
                            email.set("amankumargourh@gmail.com")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/ItsAmanOP/SendOTP.git")
                        developerConnection.set("scm:git:ssh://github.com:ItsAmanOP/SendOTP.git")
                        url.set(artifactUrl)
                    }
                }
            }
        }
    }

    signing {

//        val keyId = findProperty("signing.keyId") as String
//        val password = findProperty("signing.password") as String
//        val signingKey = findProperty("signing.armoredKey") as String
//        val sonaTypeUseName = findProperty("SONATYPE_USER_NAME") as String
//        val sonaTypePassword = findProperty("SONATYPE_PASSWORD") as String
//        val secretKeyRingFilePath = findProperty("signing.secretKeyRingFile") as String

        println("Key ID: $keyId") // prints correct key (last 8)
        println("Version: $version")
        println("Armed SigningKey: $signingKey")
        println("Password: $password") // prints correct passphrase
        println("SONATYPE_USER_NAME: $sonaTypeUseName")
        println("SONATYPE_PASSWORD: $sonaTypePassword")
//        println("Secret Key Ring File Path: $secretKeyRingFilePath")

//        useInMemoryPgpKeys(keyId, password)
//        useGpgCmd()
        useInMemoryPgpKeys(keyId, signingKey, password)
        sign(publishing.publications)
    }

    nmcp {
        publishAllPublications {
//            username = findProperty("SONATYPE_USER_NAME") as String
//            password = findProperty("SONATYPE_PASSWORD") as String

            username = sonaTypeUseName
            password = sonaTypePassword
//            publicationType = "AUTOMATIC"
            publicationType = "USER_MANAGED"
        }
    }
}