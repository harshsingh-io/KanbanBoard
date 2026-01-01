## 2024-05-23 - [Sign In Form UX]
**Learning:** Users often get stuck on the last field of a form if the keyboard "Done" action doesn't submit. Also, missing autofill hints breaks password managers.
**Action:** Always add `imeOptions="actionDone"` with an `OnEditorActionListener` to the final input field, and ensure `autofillHints` are present on all credential fields.
