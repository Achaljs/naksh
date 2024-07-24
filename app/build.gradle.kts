plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
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

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    implementation(libs.androidx.activity)
    implementation(libs.androidx.constraintlayout)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)

    implementation("com.airbnb.android:lottie:6.4.1")
    implementation("com.tbuonomo:dotsindicator:5.0")
    implementation("de.hdodenhof:circleimageview:3.1.0")
    implementation("com.android.support:multidex:1.0.3")

    implementation("com.github.denzcoskun:ImageSlideshow:0.1.2")
    implementation("io.agora.rtm:rtm-sdk:1.5.0")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2")
}