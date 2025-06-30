plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.jetbrainsKotlin)
    alias(libs.plugins.kotlinSymbolProcessing)
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.kotlin.serialization)
}

android {
    namespace = "com.example.core"
    compileSdk = 35

    defaultConfig {
        minSdk = 27

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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    // Android core bundle ->
    implementation(libs.bundles.androidx.core)

    // Unit tests bundle ->
    implementation(libs.bundles.unit.test)

    // Instrumented tests bundle ->
    implementation(libs.bundles.android.test)

    // Retrofit ->
    implementation(libs.bundles.network)

    // Coroutines ->
    implementation(libs.kotlinx.coroutines.android)

    // Timber for logs ->
    implementation(libs.timber)

    // Room dependencies ->
    implementation(libs.bundles.room)
    ksp(libs.room.compiler)

    // Serialization ->
    implementation(libs.kotlinx.serialization.json)

}