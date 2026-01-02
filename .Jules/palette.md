## 2024-05-23 - [Keyboard Form Submission]
**Learning:** Android EditTexts do not automatically submit forms on "Enter" unless `imeOptions="actionDone"` is explicitly paired with an `OnEditorActionListener`. This is a common missed opportunity in auth flows.
**Action:** Always check final form fields for `imeOptions` and add the listener to smooth out the sign-in/sign-up experience.
