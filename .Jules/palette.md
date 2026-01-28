## 2024-05-23 - [Accessibility String Naming]
**Learning:** Using generic string names like 'app_logo' for content descriptions can lead to poor accessibility if they are reused in contexts where they don't make sense or if the value is generic.
**Action:** Use 'cd_' prefix for accessibility content descriptions to clearly separate them from display text and encourage descriptive values.

## 2024-05-23 - [Generic Content Descriptions]
**Learning:** The app uses a single generic `@string/image_contentDescription` ("Image") for all image buttons and image views, which renders the app significantly less accessible for screen reader users as they cannot distinguish between actions (edit, delete, confirm) or visual elements.
**Action:** Replace generic content descriptions with specific, action-oriented strings (e.g., "Edit list", "Delete list", "Confirm add card") for all interactive elements, and context-specific descriptions for images (e.g., "Board cover image").
# Palette's Journal

## 2024-05-22 - Initial Setup
**Learning:** This is an Android project using XML layouts.
**Action:** Focus on `android:contentDescription`, `android:hint`, `android:inputType`, and `android:autofillHints` in XML files.

**Learning:** Android apps often miss `android:autofillHints` on `EditText` fields, which is a low-hanging fruit for accessibility. It significantly helps users with password managers.
**Action:** Always check `EditText` fields for `autofillHints` and `inputType` mismatches (like name field having email input type).


## 2024-05-23 - Initial Exploration
**Learning:** This is an Android App using XML layouts. I cannot run the app or use typical web dev tools (pnpm, npm). I need to rely on static analysis of XML and Kotlin files.
**Action:** Focus on XML layout improvements for accessibility (hints, input types, content descriptions) and Kotlin code for small logic enhancements (like validating input before submission).

## 2024-05-22 - [Accessibility: Content Descriptions]
**Learning:** Found widespread use of generic "Image" or "Image Description" content descriptions in XML layouts, and decorative images being announced. This creates a noisy and confusing experience for screen reader users.
**Action:** When adding ImageViews or ImageButtons, always ask: "Does this convey information?" If yes, add a specific, localized string. If no, use `importantForAccessibility="no"`. Never use generic placeholders like "Image".


## 2024-05-24 - [Form Navigation Pattern]
**Learning:** Users expect keyboard navigation (Next/Done) in forms. Implementing `imeOptions` without `OnEditorActionListener` is incomplete.
**Action:** Always pair `imeOptions="actionDone"` with an `OnEditorActionListener` that triggers the submit button programmatically.
