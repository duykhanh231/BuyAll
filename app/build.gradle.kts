plugins {
    id("com.android.application") // Plugin Android Application
    id("com.google.gms.google-services") // Plugin Google Services
}

android {
    namespace = "com.group9.buyall"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.group9.buyall"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        getByName("release") {
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
}

dependencies {
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.firebase.database)
    implementation(libs.firebase.auth) // Firebase Auth từ `libs.versions.toml`
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    implementation(libs.flexbox)
    implementation(libs.glide)
    implementation (libs.retrofit)
    implementation (libs.converter.gson)
    implementation (libs.room.runtime)
    annotationProcessor (libs.room.compiler)
}
