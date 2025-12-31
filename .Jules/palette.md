## 2024-05-22 - [Keyboard & Autofill UX]
**Learning:** Android's `OnEditorActionListener` combined with `imeOptions="actionDone"` provides a significantly smoother form submission experience than button-only interactions, especially on mobile devices where the keyboard covers the submit button.
**Action:** Always check form inputs for `imeOptions` and `autofillHints` during UX reviews. Ensure the last input field triggers the submit action.
