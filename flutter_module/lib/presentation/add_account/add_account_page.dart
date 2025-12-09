import 'package:flutter/material.dart';

class AddAccountPage extends StatefulWidget {
  const AddAccountPage({super.key});

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
            "Add Account",
            style: TextStyle(color: Colors.white, fontWeight: FontWeight.bold),
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
                Text("Add Account Page", style: TextStyle(fontSize: 22)),
                SizedBox(height: 50,),
                ElevatedButton(onPressed: () {
                  // Navigate Back to Native Activity with Some data.
                }, child: Text("Back to Native"))
              ],
            ),
          ),
        ),
      ),
    );
  }
}
