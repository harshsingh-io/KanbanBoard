## 2024-05-23 - [Generic Content Descriptions]
**Learning:** The app uses a single generic `@string/image_contentDescription` ("Image") for all image buttons and image views, which renders the app significantly less accessible for screen reader users as they cannot distinguish between actions (edit, delete, confirm) or visual elements.
**Action:** Replace generic content descriptions with specific, action-oriented strings (e.g., "Edit list", "Delete list", "Confirm add card") for all interactive elements, and context-specific descriptions for images (e.g., "Board cover image").
