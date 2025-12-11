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
  final Map<String, List<String>> categoryGroups = {
    "Social": ["Facebook", "Instagram", "Twitter", "Snapchat", "YouTube"],

    "E-Commerce": ["Amazon", "Flipkart", "Myntra", "Zomato", "Swiggy"],

    "Travel": ["IRCTC", "MakeMyTrip", "Yatra"],

    "Banking": [
      "ICICI Bank",
      "SBI",
      "PSB",
      "ICICI Credit Card",
      "PSB Debit Card",
    ],

    "Government Services": ["EPFO", "Post Office (PO)"],

    "Office / Corporate": [
      "Zoho",
      "Slack",
      "Notion",
      "Capgemini",
      "TCS",
      "Accenture",
    ],
  };

  String? selectedCategory;
  String? selectedPlatform;

  final TextEditingController usernameController = TextEditingController();
  final TextEditingController passwordController = TextEditingController();

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
            style: TextStyle(
              color: Colors.white,
              fontWeight: FontWeight.bold,
              fontSize: 16,
            ),
          ),
          leading: IconButton(
            onPressed: () {
              Navigator.pop(context);
            },
            icon: const Icon(Icons.arrow_back),
            color: Colors.white,
          ),
        ),
        body: Padding(
          padding: const EdgeInsets.all(16),
          child: SingleChildScrollView(
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: [
                // CATEGORY DROPDOWN
                const Text(
                  "Category",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w600,
                    color: Color(0xFF702963),
                  ),
                ),
                const SizedBox(height: 4),

                Container(
                  padding: const EdgeInsets.symmetric(horizontal: 12),
                  decoration: BoxDecoration(
                    border: Border.all(color: Colors.grey),
                    borderRadius: BorderRadius.circular(8),
                  ),
                  child: DropdownButton<String>(
                    isExpanded: true,
                    underline: const SizedBox(),
                    value: selectedCategory,
                    hint: const Text("Select Category"),
                    onChanged: (value) {
                      setState(() {
                        selectedCategory = value;
                        selectedPlatform = null;
                      });
                    },
                    items: categoryGroups.keys.map((cat) {
                      return DropdownMenuItem<String>(
                        value: cat,
                        child: Text(cat),
                      );
                    }).toList(),
                  ),
                ),
                const SizedBox(height: 10),

                // Platform dropdown depends on category
                if (selectedCategory != null) ...[
                  const Text(
                    "Platform",
                    style: TextStyle(
                      fontSize: 16,
                      fontWeight: FontWeight.w600,
                      color: Color(0xFF702963),
                    ),
                  ),
                  const SizedBox(height: 4),
                  Container(
                    padding: const EdgeInsets.symmetric(horizontal: 12),
                    decoration: BoxDecoration(
                      border: Border.all(color: Colors.grey),
                      borderRadius: BorderRadius.circular(8),
                    ),
                    child: DropdownButton<String>(
                      isExpanded: true,
                      underline: const SizedBox(),
                      value: selectedPlatform,
                      hint: const Text("Select Platform"),
                      items: categoryGroups[selectedCategory]!
                          .map(
                            (platform) => DropdownMenuItem(
                              value: platform,
                              child: Text(platform),
                            ),
                          )
                          .toList(),
                      onChanged: (value) {
                        setState(() => selectedPlatform = value);
                      },
                    ),
                  ),
                  const SizedBox(height: 10),
                ],

                const Text(
                  "Username / Email / Phone",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w600,
                    color: Color(0xFF702963),
                  ),
                ),
                const SizedBox(height: 4),
                TextField(
                  controller: usernameController,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                    hintText: "Enter Username",
                  ),
                ),

                const SizedBox(height: 10),

                const Text(
                  "Password / PIN",
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w600,
                    color: Color(0xFF702963),
                  ),
                ),
                const SizedBox(height: 4),
                TextField(
                  controller: passwordController,
                  obscureText: true,
                  decoration: InputDecoration(
                    border: OutlineInputBorder(
                      borderRadius: BorderRadius.circular(8),
                    ),
                    hintText: "Enter Password",
                  ),
                ),

                const SizedBox(height: 40),

                SizedBox(
                  width: double.infinity,
                  child: ElevatedButton(
                    style: ElevatedButton.styleFrom(
                      backgroundColor: Color(0xFF702963),
                      shape: RoundedRectangleBorder(
                        borderRadius: BorderRadius.circular(8),
                      ),
                    ),
                    onPressed: () {
                      if (checkValidations()) {
                        final record = {
                          "category": selectedCategory,
                          "platform": selectedPlatform,
                          "username": usernameController.text,
                          "password": passwordController.text,
                        };

                        print("Saved Record: $record");

                        // Navigate Back to Native Activity with Some data.
                        final platform = MethodChannel(
                          FlutterChannelKeys.loginChannel,
                        );
                        platform.invokeMethod("saveRecord", record);
                      }
                    },
                    child: Text(
                      "Save Record",
                      style: TextStyle(color: Colors.white),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  bool checkValidations() {
    // handle validations
    if (selectedCategory == null ||
        selectedPlatform == null ||
        usernameController.text.isEmpty ||
        passwordController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(

        const SnackBar(
          content: Text("Please fill all fields properly."),
          backgroundColor: Color(0xFF702963),
          behavior: SnackBarBehavior.floating,
          margin: EdgeInsets.all(10),
        ),
      );
      return false;
    } else {
      return true;
    }
  }
}
