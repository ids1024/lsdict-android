buildscript {
    val kotlinVersion by extra("2.0.10")
}

plugins {
    val kotlinVersion: String by rootProject.extra
    id("com.android.application") version "8.13.2"
    id("org.jetbrains.kotlin.android") version kotlinVersion
}

kotlin {
    jvmToolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

android {
    compileSdkVersion(36)

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        minSdk { version = release(21) }
        targetSdk = 36
    }

    signingConfigs {
        register("release") {
            storeFile = file("release.keystore")
            storePassword = System.getenv("KEYPWD")
            keyAlias = "upload"
            keyPassword = System.getenv("KEYPWD")
        }
    }

    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
            // signingConfig = signingConfigs.release
            proguardFile(getDefaultProguardFile("proguard-android.txt"))
        }
    }
    namespace = "com.ids1024.lsdict"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }

    kotlinOptions {
        jvmTarget = "22"
    }
}

repositories {
    mavenCentral()
    google()
}

val ktlint: Configuration by configurations.creating

dependencies {
    val kotlinVersion: String by rootProject.extra
    ktlint("com.pinterest.ktlint:ktlint-cli:1.7.1")
    //noinspection GradleDependency
    implementation("org.jetbrains.kotlin:kotlin-stdlib:$kotlinVersion")
    implementation("com.readystatesoftware.sqliteasset:sqliteassethelper:+")
    implementation("androidx.appcompat:appcompat:1.7.1")
    implementation("com.google.android.material:material:1.13.0")
}

tasks.register<JavaExec>("ktlint") {
    group = "verification"
    description = "Check Kotlin code style."
    classpath = ktlint
    mainClass = "com.pinterest.ktlint.Main"
    args("src/**/*.kt", "build.gradle.kts")
}

tasks.register<JavaExec>("ktlintFormat") {
    group = "formatting"
    description = "Fix Kotlin code style deviations."
    classpath = ktlint
    mainClass = "com.pinterest.ktlint.Main"
    args("-F", "src/**/*.kt", "build.gradle.kts")
}
