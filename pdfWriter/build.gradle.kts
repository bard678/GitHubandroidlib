plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("maven-publish")

}

android {
    namespace = "com.pdf.pdfwriter"
    compileSdk = 35

    defaultConfig {
        minSdk = 24

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

dependencies {

     implementation("io.coil-kt:coil-compose:2.0.0")
    implementation ("com.itextpdf:itext7-core:7.2.2")
}


publishing {
    publications {
        create<MavenPublication>("release") {
            groupId = "com.github.YOUR_USERNAME"
            artifactId = "pdfwriter"
            version = "1.0.0"

            afterEvaluate {
                from(components["release"])
            }
        }
    }

    repositories {
        maven {
            name = "GitHubPackages"
            url = uri("https://maven.pkg.github.com/bard678/PDFWriter")

            credentials {
                username = "bard678"
                password =   "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"         }
        }
    }
}
