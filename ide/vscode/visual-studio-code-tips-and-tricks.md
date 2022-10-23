# Visual studio code user guide

## Basic
Click `Help > Welcome` to go to Welcome page.

### Interactive playground
Click `Help > Interactive Playground` to go to Interactive playground page.

### Command Palette
`Ctrl+Shift+P`.

### Keyboard reference sheets
Download the keyboard shortcut reference sheet for your platform (macOS, Windows, Linux).

### Quick Open
Quickly open files by `Ctrl+P`.

## Command line
``` bash
# open code with current directory
code .

# open the current directory in the most recently used code window
code -r .

# create a new window
code -n

# change the language
code --locale=es

# open diff editor
code --diff <file1> <file2>

# see help options
code --help
```

## Status Bar
Check Errors and warnings by `Ctrl+Shift+M`.

## Customization

### Customize your keyboard shortcuts
`Ctrl+K Ctrl+S`.
Or you can add your own keybindings to the `keybindings.json` file.

## Extensions
use UI.

## Files and folders
`Ctrl+` `.

### Toggle Sidebar
`Ctrl+B`.

### Zen mode
`Ctrl+K Z`. Press Esc twice to exit Zen Mode.

### Side by side editing
`Ctrl+\`

## Editing hacks

### Copy line up / down
`Shift+Alt+Up` or `Shift+Alt+Down`.

### Move line up and down
`Alt+Up` or `Alt+Down`.

### Navigate to a specific line
`Ctrl+G`.

### Trim trailing whitespace
`Ctrl+K Ctrl+X`.

### Code formatting
Currently selected source code: `Ctrl+K Ctrl+F`.
Whole document format: `Shift+Alt+F`.

### Navigate to beginning and end of file
`Ctrl+Home` and `Ctrl+End`.

### Side by side Markdown edit and preview
In a Markdown file, use `Ctrl+K V`

## IntelliSense
`Ctrl+Space` to trigger the Suggestions widget.

### Peek
Select a symbol then type `Alt+F12`.

### Go to Definition
Select a symbol then type `F12`.

### Peek References
Select a symbol then type `Shift+F12`.

### Rename Symbol
Select a symbol then type `F2`.

### Emmet syntax

## Git integration
`Ctrl+Shift+G`.

## Debugging

### Configure debugger
Open the Command Palette (`Ctrl+Shift+P`) and select `Debug: Open launch.json`.