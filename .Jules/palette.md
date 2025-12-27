## 2024-05-23 - [Input Type and Autofill Hints]
**Learning:** Found that `inputType="textEmailAddress"` was incorrectly used for a Name field, which would show the wrong keyboard layout to users. Also, missing `autofillHints` makes it harder for password managers to assist users.
**Action:** Always verify `inputType` matches the data expected (e.g., `textPersonName` for names) and include `autofillHints` for standard fields (email, password, name, phone) to improve form completion speed and accessibility.
