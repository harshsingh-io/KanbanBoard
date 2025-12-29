## 2024-05-23 - [Input Type Misconfigurations]
**Learning:** Found that `inputType` is sometimes copy-pasted incorrectly (e.g., `textEmailAddress` for a name field), which hurts UX by showing the wrong keyboard.
**Action:** Always verify `inputType` matches the semantic meaning of the field when reviewing layouts.
