## 2024-04-12 - [Keyboard Efficiency in Forms]
**Learning:** Users often expect the "Enter" or "Done" key on the soft keyboard to submit simple forms, but this requires explicit `imeOptions` and `OnEditorActionListener` implementation in Android.
**Action:** Always check `EditText` fields in forms for `imeOptions="actionDone"` (for the last field) and verify a listener is attached to trigger the submit action.

## 2024-04-12 - [Generic Content Descriptions]
**Learning:** The project uses a generic `@string/image_contentDescription` ("Image") for many image views, including interactive ones. This provides poor accessibility.
**Action:** When touching a layout file, check if `contentDescription` is generic and replace it with a specific, functional description (e.g., "Select board cover image").
