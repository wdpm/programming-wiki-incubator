str = 'https://path/to/here.com'

removeprefix = str.removeprefix('https://')

result = removeprefix.removesuffix('.com')

print(result)
# path/to/here
