import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_module/utils/FlutterChannelKeys.dart';

class AddAccountPage extends StatefulWidget {

  final String username;

  const AddAccountPage({super.key, required this.username});

  @override
  State<AddAccountPage> createState() => _AddAccountPageState();
}

class _AddAccountPageState extends State<AddAccountPage> {
  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        Navigator.pop(context);
        return true;
      },
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: Color(0xFF702963),
          title: Text(
            "Account Holder - ${widget.username}",
            style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold, fontSize: 16),
          ),
          leading: IconButton(
            onPressed: () {
              Navigator.pop(context);
            },
            icon: const Icon(Icons.arrow_back),
            color: Colors.white,
          ),
        ),
        body: Center(
          child: SingleChildScrollView(
            child: Column(
              children: [
                Text("Store New Creds Page", style: TextStyle(fontSize: 22)),
                SizedBox(height: 50),
                ElevatedButton(
                  onPressed: () {
                    // Navigate Back to Native Activity with Some data.
                    final platform = MethodChannel(
                      FlutterChannelKeys.loginChannel,
                    );
                    platform.invokeMethod("sendAccountDetails", widget.username);
                  },
                  child: Text("Back to Native"),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
