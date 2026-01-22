import importlib.resources

# Assume that your package is named `my_package`
# and the resource you want to read is in a file named `my_file.txt`
# located in the directory `my_package/resources`

with importlib.resources.open_text('my_package.resources', 'my_file.txt') as f:
    contents = f.read()

print(contents)
