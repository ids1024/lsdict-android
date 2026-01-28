plugins {
    id("com.android.application") version "9.0.0"
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
            proguardFile(getDefaultProguardFile("proguard-android-optimize.txt"))
        }
    }
    namespace = "com.ids1024.lsdict"

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_22
        targetCompatibility = JavaVersion.VERSION_22
    }
}

repositories {
    mavenCentral()
    google()
}

val ktlint: Configuration by configurations.creating

dependencies {
    ktlint("com.pinterest.ktlint:ktlint-cli:1.7.1")
    //noinspection GradleDependency
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
