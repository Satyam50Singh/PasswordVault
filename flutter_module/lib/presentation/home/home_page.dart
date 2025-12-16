import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

import '../../utils/FlutterChannelKeys.dart';
import '../add_account/add_account_page.dart';

class HomeScreen extends StatefulWidget {
  const HomeScreen({super.key});

  @override
  State<HomeScreen> createState() => _HomeScreenState();
}

class _HomeScreenState extends State<HomeScreen> {
  static const platform = MethodChannel(
    FlutterChannelKeys.loginChannel,
  );

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
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Color(0xFF702963),
        title: Text(
          "$username's vault",
          style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
        ),
        leading: IconButton(
          onPressed: () {
            platform.invokeMethod("closeFlutter");
            SystemNavigator.pop();
          },
          icon: const Icon(Icons.arrow_back),
          color: Colors.white,
        ),
      ),
      backgroundColor: Colors.white,
      body: Center(
        child: SingleChildScrollView(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Text(
                "Welcome $username!",
                style: TextStyle(fontSize: 28, fontWeight: FontWeight.bold),
              ),
              SizedBox(height: 40),
              ElevatedButton(
                onPressed: () {
                  print("Button pressed");
                  Navigator.push(
                    context,
                    MaterialPageRoute(builder: (_) => AddAccountPage(username: username)),
                  );
                },
                child: const Text("Store new credentials"),
              ),
            ],
          ),
        ),
      ),
    );
  }
}
