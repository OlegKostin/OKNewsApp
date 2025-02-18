import java.util.Properties

plugins {
  alias(libs.plugins.android.library)
  alias(libs.plugins.kotlin.android)
  alias(libs.plugins.kotlinSerialization)

}

android {
  namespace = "com.olegkos.newsapi"
  compileSdk = 35

  defaultConfig {
    minSdk = 26

    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    consumerProguardFiles("consumer-rules.pro")
  }
  val localProperties = Properties()
  val localPropertiesFile = File(rootDir, "apik.properties")
  if (localPropertiesFile.exists() && localPropertiesFile.isFile) {
    localPropertiesFile.inputStream().use {
      localProperties.load(it)
    }
  }
  buildTypes {
    release {
      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
      buildConfigField(
        type = "String",
        name = "API_KEY_PROD",
        value = localProperties.getProperty("API_KEY"),
      )

    }
    debug {
      buildConfigField(
        type = "String",
        name = "API_KEY",
        value = localProperties.getProperty("API_KEY"),
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
    buildConfig = true
  }
}

dependencies {
  api(libs.retrofit) /*TODO("change back")*/
  api(libs.kotlin.serialization.json)
  api(libs.retrofit2.kotlinx.serialization.converter)
  implementation(libs.androidx.core.ktx)
  implementation(libs.androidx.appcompat)
  implementation(libs.kotlin.coroutine.core)
  implementation(libs.logging.interceptor)

  implementation (libs.dagger)


}