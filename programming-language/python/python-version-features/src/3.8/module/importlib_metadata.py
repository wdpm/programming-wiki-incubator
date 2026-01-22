from importlib.metadata import version, requires, files

print(version('requests'))

print(list(requires('requests')))

print(list(files('requests'))[:5])
