## 2024-05-22 - [Accessibility: Content Descriptions]
**Learning:** Found widespread use of generic "Image" or "Image Description" content descriptions in XML layouts, and decorative images being announced. This creates a noisy and confusing experience for screen reader users.
**Action:** When adding ImageViews or ImageButtons, always ask: "Does this convey information?" If yes, add a specific, localized string. If no, use `importantForAccessibility="no"`. Never use generic placeholders like "Image".
