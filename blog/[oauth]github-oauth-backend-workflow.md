# OAuth GitHub Login

```python
# Somewhere in webapp_example.py, before the app.run for example
import os

# disable callback SSL check
os.environ['OAUTHLIB_INSECURE_TRANSPORT'] = '1'
# or export OAUTHLIB_INSECURE_TRANSPORT=1

# Credentials you get from registering a new application
client_id = 'd9d39cdf5d7fe3379049'
client_secret = 'replace-with-yours'

# OAuth endpoints given in the GitHub API documentation
authorization_base_url = 'https://github.com/login/oauth/authorize'
token_url = 'https://github.com/login/oauth/access_token'

from requests_oauthlib import OAuth2Session

github = OAuth2Session(client_id)

# Redirect user to GitHub for authorization
authorization_url, state = github.authorization_url(authorization_base_url)
print('Please go here and authorize,', authorization_url)
# Please go here and authorize,
# https://github.com/login/oauth/authorize?response_type=code&client_id=d9d39cdf5d7fe3379049&state=0MiDNLC1Oxf7rwTMtGaEa2a9lSFKAI

# Get the authorization verifier code from the callback url
redirect_response = input('Paste the full redirect URL here:')
# Paste the full redirect URL here:
# http://localhost:8080/oauth/redirect?code=77a19a377dc2def3d589&state=0MiDNLC1Oxf7rwTMtGaEa2a9lSFKAI

# Fetch the access token
github.fetch_token(token_url, client_secret=client_secret,
                   authorization_response=redirect_response)

# Fetch a protected resource, i.e. user profile
r = github.get('https://api.github.com/user')
print(r.content)
# b'{"login":"wdpm","id":14016242,"node_id":"MDQ6VXNlcjE0MDE2MjQy",......
```