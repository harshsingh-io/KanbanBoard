## 2024-05-23 - [Form UX Improvements]
**Learning:** Android authentication forms often lack `autofillHints` and `imeOptions`, which significantly degrades the user experience by preventing password managers from working and forcing manual keyboard dismissal.
**Action:** Always check `EditText` elements in auth flows for `autofillHints` matching their content and `imeOptions` (`actionNext` or `actionDone`) to streamline the keyboard interaction.
