import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

void main() {
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {

  static const platform = MethodChannel(
      "com.satya.passwordvault/login_channel");

  String username = "";

  @override
  void initState() {
    super.initState();

    platform.setMethodCallHandler((call) async {
      if (call.method == "sendUsername") {
        setState(() {
          username = call.arguments.toString();
          print(username);
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        backgroundColor: Colors.white,
        body: Center(
          child: Text(
            "Welcome $username!",
            style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold
            ),
          ),
        ),
      ),
    );
  }
}