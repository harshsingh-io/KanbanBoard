## 2024-05-24 - [Keyboard Navigation in Forms]
**Learning:** Android forms often neglect keyboard navigation (`imeOptions`). Adding `actionDone` listener significantly improves flow by removing the need to hide the keyboard to submit.
**Action:** Always check `imeOptions` and `autofillHints` on input fields. Implement `OnEditorActionListener` for the final field in a form.
