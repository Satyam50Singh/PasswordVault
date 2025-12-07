pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
        maven(url = "https://storage.googleapis.com/download.flutter.io")
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.PREFER_SETTINGS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://storage.googleapis.com/download.flutter.io")
    }
}

rootProject.name = "PasswordVault"

// Include native Android app module
include(":app")

// Flutter module (source mode)
include(":flutter")
project(":flutter").projectDir = File("../flutter_module/.android/Flutter")

// IMPORTANT: Load Flutter's generated Gradle config
apply {
    from(File("../flutter_module/.android/include_flutter.groovy"))
}
