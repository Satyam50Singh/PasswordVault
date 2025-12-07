# 📱 PasswordVault
**Native Android App integrated with Jetpack Compose + Flutter Module**

This repository contains an Android application built using **XML + Jetpack Compose**, with an embedded **Flutter module** for hybrid UI screens.  
It demonstrates how to integrate Flutter inside an existing Android app while keeping the build lightweight and modular.

---

## 🚀 Features

### **Android (Native)**
- XML + Jetpack Compose hybrid UI
- ComposeView inside XML layouts
- MVVM architecture
- Secure password management workflows
- ViewBinding + Compose interoperability
- Kotlin DSL (KTS) based Gradle setup

### **Flutter Module**
- Fully embedded Flutter module
- Launched using `FlutterActivity`
- Cached FlutterEngine for faster startup
- ABI filtering to reduce build size
- Clean integration with Android application

---

## 📂 Project Structure


Both the native Android app and Flutter module exist in the **same root repository**.

```bash
root/
│── PasswordVault/ # Main Android project
│ ├── app/
│ ├── build.gradle.kts
│ ├── settings.gradle.kts
│ └── ...
│
└── flutter_module/ # Flutter module
├── lib/
├── .android/ # Generated (ignored)
├── .ios/ # Generated (ignored)
├── pubspec.yaml
└── ...
```
## 🛠 Tech Stack

### **Android**
- Kotlin
- Jetpack Compose
- XML + ComposeView
- Material Design 3
- AndroidX Libraries
- ViewBinding
- Kotlin DSL (KTS)

### **Flutter**
- Flutter Module (`flutter create -t module flutter_module`)
- Dart
- Material 3 UI


## 🔗 Flutter Integration (Summary)

### **1. Create Flutter Module**

```bash
flutter create -t module flutter_module
```

### **2. Add Flutter to settings.gradle.kts**
```bash
setBinding(Binding(mapOf("gradle" to this)))
evaluate(File(rootDir, "flutter_module/.android/include_flutter.groovy"))

include(":flutter")
project(":flutter").projectDir = File("../flutter_module")
```

### **3. Add Flutter dependency in app/build.gradle.kts**
```bash
dependencies {
     implementation(project(":flutter"))
}
```

### **4. Configure AndroidManifest.xml**
```bash
<application
android:name=".BaseApplication"
... >

    <!-- Flutter Activity -->
    <activity
        android:name="io.flutter.embedding.android.FlutterActivity"
        android:exported="true" />
</application>
```

### **5. Launch Flutter Screen from Android**
```bash
val engineId = "my_engine_id"
FlutterEngineCache.getInstance().put(engineId, FlutterEngine(this))

val intent = FlutterActivity
.withCachedEngine(engineId)
.initialRoute("/")
.build(this)

startActivity(intent)
```

### **6. Create BaseApplication.kt: Initialize Flutter engine caching in your BaseApplication**
```bash
class BaseApplication : Application() {
override fun onCreate() {
super.onCreate()

        // Pre-warm Flutter engine
        val engineId = "my_engine_id"
        val flutterEngine = FlutterEngine(this)
        FlutterEngineCache.getInstance().put(engineId, flutterEngine)
    }
}
```

### **Run Flutter module independently**
- cd flutter_module
- flutter run
