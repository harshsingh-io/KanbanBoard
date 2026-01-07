
## 2024-05-23 - Initial Exploration
**Learning:** This is an Android App using XML layouts. I cannot run the app or use typical web dev tools (pnpm, npm). I need to rely on static analysis of XML and Kotlin files.
**Action:** Focus on XML layout improvements for accessibility (hints, input types, content descriptions) and Kotlin code for small logic enhancements (like validating input before submission).

## 2024-05-22 - [Accessibility: Content Descriptions]
**Learning:** Found widespread use of generic "Image" or "Image Description" content descriptions in XML layouts, and decorative images being announced. This creates a noisy and confusing experience for screen reader users.
**Action:** When adding ImageViews or ImageButtons, always ask: "Does this convey information?" If yes, add a specific, localized string. If no, use `importantForAccessibility="no"`. Never use generic placeholders like "Image".

