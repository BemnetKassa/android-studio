plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.basicsofict"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.basicsofict"
        minSdk = 21
        targetSdk = 34
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
}

dependencies {
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.recyclerview:recyclerview:1.3.1")
    implementation("androidx.cardview:cardview:1.0.0")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")

    // *** FIX: ADD THE MISSING VIEWMODEL DEPENDENCY WITH CORRECT SYNTAX ***
    implementation("androidx.lifecycle:lifecycle-viewmodel:2.6.2")

    implementation("com.google.code.gson:gson:2.10.1")
    implementation("androidx.navigation:navigation-fragment:2.5.3")
    implementation("androidx.navigation:navigation-ui:2.5.3")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    // *** FIX: ADD THIS BLOCK TO RESOLVE KOTLIN VERSION CONFLICTS ***
    // This tells Gradle to force all Kotlin libraries to use a single version
    constraints {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk7:1.8.10") {
            because("Align all kotlin-stdlib libraries to a single version")
        }
        implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.8.10") {
            because("Align all kotlin-stdlib libraries to a single version")
        }
    }
}
