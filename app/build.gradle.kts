plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.google.gms.google.services)
}

android {
    namespace = "com.example.nakshatratak"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.nakshatratak"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

            multiDexEnabled =  true

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            abiFilters.add("armeabi-v7a")


        }
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            isShrinkResources = true
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    implementation(libs.firebase.messaging)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

   // implementation(libs.lottie)
    implementation(libs.dotsindicator)
    implementation(libs.circleimageview)

    implementation(libs.imageslideshow)


    implementation(libs.chat.sdk)
    implementation (libs.full.rtc.basic)

    implementation ("com.squareup.retrofit2:retrofit:2.9.0") // Latest version
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0") // Latest version
    implementation ("com.squareup.okhttp3:okhttp:4.10.0") // Latest version
    implementation ("com.squareup.okhttp3:logging-interceptor:4.10.0") // For logging network requests
    implementation ("com.github.gabriel-TheCode:AestheticDialogs:1.3.8")
}