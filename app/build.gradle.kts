plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
//    alias(libs.plugins.googleServices)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room)
    alias(libs.plugins.hiltAndroid)

}

android {
    namespace = "com.aakash.runningapp"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.aakash.runningapp"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    room {
        schemaDirectory("$projectDir/schemas")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
            excludes += "/META-INF/gradle/incremental.annotation.processors"

        }
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(libs.androidx.navigation.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.ui)

    implementation(libs.splash.screen)

    // Hilt
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    //Retrofit
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)
    implementation(libs.androidx.json.serialization)

    //CameraX
    implementation(libs.camera.core)
    implementation(libs.camera.compose)
    implementation(libs.camera.lifecycle)
    implementation(libs.camera.view)

    //Coroutines
    implementation(libs.coroutine.core)
    implementation(libs.coroutine.play.services)

    //Location
    implementation(libs.location)

    //Room database
    implementation(libs.room.runtime)
    implementation(libs.room.ktx)
    implementation(libs.androidx.navigation.runtime.ktx)
    ksp(libs.room.compiler)
    annotationProcessor(libs.room.compiler)

    //Shared Preferences and DataStore
    implementation(libs.androidx.preferences)
    implementation(libs.androidx.datastore)

    //Coil
    implementation(libs.coil.kt.compose)
    implementation(libs.coil.kt.svg)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}