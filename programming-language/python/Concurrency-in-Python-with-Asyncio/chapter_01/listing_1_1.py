import requests

# IO
response = requests.get('https://www.example.com')

items = response.headers.items()

# CPU
headers = [f'{key}: {header}' for key, header in items]

# CPU
formatted_headers = '\n'.join(headers)

with open('headers.txt', 'w') as file:
    # IO
    file.write(formatted_headers)
