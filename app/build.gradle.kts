plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    id("com.google.gms.google-services")
    id("kotlin-kapt")
    id ("kotlin-parcelize")
}

android {
    namespace = "com.noelayllon.apprestauranteeee"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.noelayllon.apprestauranteeee"
        minSdk = 31
        targetSdk = 35
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
        viewBinding = true
    }





}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.firebase.auth)
    implementation(libs.firebase.firestore)
    implementation(libs.androidx.adapters)
    implementation(libs.androidx.media3.common.ktx)
    implementation(libs.play.services.cast.framework)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    // Room
    implementation (libs.androidx.room.runtime)
    annotationProcessor (libs.androidx.room.compiler)
    kapt (libs.androidx.room.compiler.v270) // For Kotlin projects

    implementation (libs.androidx.room.ktx)

// Lifecycle
    implementation (libs.androidx.lifecycle.runtime.ktx)

// Coroutines
    implementation (libs.kotlinx.coroutines.android)

    //fire base
    implementation(libs.firebase.bom)

    implementation (libs.androidx.gridlayout)

    implementation("com.squareup.picasso:picasso:2.8")




}