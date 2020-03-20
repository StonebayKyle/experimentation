Tool created by StonebayKyle
------------------------------
Do not redistribute this software without receiving explicit permission from the author.
------------------------------
This is a simple program that allows you to automatically format text for trading purposes.
It was created with Warframe trading in mind, but can be used for many other applications due to the customizable options.


REQUIREMENTS:
Java 1.8 - Download at https://java.com/en/download/

WHAT YOU SHOULD KNOW:
--------- Buttons and Fields ---------
Format! - Formats your list from the large text area according to the entered settings.
        - When making changes, be sure to go back (undo) to your original, unmodified, text, or it will do a format on top of another format.
Undo    - Undoes a format, able to revert back up to 20 formats. Useful to undo, make changes, and format once again to make changes.

ALL FIELDS: All characters matter, including spaces. 

Linkable Brackets - Places opening and closing brackets around each item

Prefix - Places the specified text before each item (Ex: P[item])
Suffix - Places the specified text after  each item (Ex: [item]S)
Between - Places the specified text in between each item (Ex: [item]B[item])

Separation Marker - Tells the formatter where to distinguish items (Ex: item1,item2). Default: ,

List Prefix - Places the specified text before each list (Ex: WTS[list])
List Suffix - Places the specified text after  each list (Ex: [list]PMO)

Put Spaces Between Items - Puts spaces between items, after any stated suffix and before any stated prefix. (Ex: [item] [item])
Remove Outstanding Spaces - Removes spaces at the beginning and ends of items. This ensures "item, item, " does not become "[item][ item]", making the format unlinkable or messy.

Max Characters Per Copy: __
    - When an option other than "None" is selected, it breaks your input into different parts, labled "Copy (#)".
    - The size of each part is dependent upon the preset selected, or a custom input.
    - This is useful for automatically preparing your text to be chat channel compatible in-game, where character limits can be annoying to circumvent.
    - If an item (or items) is larger than the selected character limit, they will be inserted into the "Overflow" section at the bottom of the output.


--------- Files ---------
RECOMMENDED: Place this program in its own folder, so your data isn't accidentally overwritten.
input.txt - User's initial input into the main text box, saved upon pressing "Format!" (WARNING: will be overwritten by future formats)
output.txt - Formatted result based on the user's input and settings. Result is also printed in the main text box.
config.properties (TODO) - Stores user preferences for each setting, saved upon pressing "Save Preferences."




