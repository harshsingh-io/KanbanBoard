## 2024-05-23 - [Accessibility String Naming]
**Learning:** Using generic string names like 'app_logo' for content descriptions can lead to poor accessibility if they are reused in contexts where they don't make sense or if the value is generic.
**Action:** Use 'cd_' prefix for accessibility content descriptions to clearly separate them from display text and encourage descriptive values.

## 2024-05-23 - [Generic Content Descriptions]
**Learning:** The app uses a single generic `@string/image_contentDescription` ("Image") for all image buttons and image views, which renders the app significantly less accessible for screen reader users as they cannot distinguish between actions (edit, delete, confirm) or visual elements.
**Action:** Replace generic content descriptions with specific, action-oriented strings (e.g., "Edit list", "Delete list", "Confirm add card") for all interactive elements, and context-specific descriptions for images (e.g., "Board cover image").

## 2024-05-23 - [Android Input Handling]
**Learning:** Input fields for custom names (like Project/Board titles) often default to `inputType="text"`, which is tedious for users. `textCapWords` is a simple flag that significantly improves the experience. Also, `importantForAutofill="no"` is critical for these custom fields to prevent the keyboard from suggesting irrelevant addresses or names.
**Action:** Always verify `inputType` matches the semantic data (e.g., capitalization for titles) and manage autofill explicitly.
