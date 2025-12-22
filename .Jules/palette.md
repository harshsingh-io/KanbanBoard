## 2024-05-22 - [Adding Autofill Hints]
**Learning:** Android apps often miss `android:autofillHints` on `EditText` fields, which is a low-hanging fruit for accessibility. It significantly helps users with password managers.
**Action:** Always check `EditText` fields for `autofillHints` and `inputType` mismatches (like name field having email input type).
