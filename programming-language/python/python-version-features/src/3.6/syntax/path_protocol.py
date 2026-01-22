import pathlib

# Create a path object for the file "example.txt"
file_path = pathlib.Path("file:///tmp/example.txt")

# Check if the file exists
if file_path.exists():
    # Read the contents of the file
    with file_path.open("r") as file:
        contents = file.read()
    print(contents)
else:
    print("File not found")
