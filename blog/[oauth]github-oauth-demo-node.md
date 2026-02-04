A very simple demo of OAuth 2.0 using Node.js，to add GitHub login to your app and access GitHub API.

## Step one: register the app

Register the app on Github : https://github.com/settings/applications/new .

- "Application name" field, enter any name you like.
- "Homepage URL" field, enter "http://localhost:8080/ ".
- "callback URL" field, enter "http://localhost:8080/oauth/redirect ".

Once register, you will get a client ID and a client secret.

## Step two: create the code

`public/index.html`
```html
<!DOCTYPE html>
<html>

<head>
        <meta charset="utf-8" />
        <meta http-equiv="X-UA-Compatible" content="IE=edge">
        <title>Node OAuth2 Demo</title>
        <meta name="viewport" content="width=device-width, initial-scale=1">
</head>

<body>
        <a id="login">Login with GitHub</a>

<script>
  // fill in your client_id
  const client_id = '98ada85e5491d9b333ba';

  const authorize_uri = 'https://github.com/login/oauth/authorize';
  const redirect_uri = 'http://localhost:8080/oauth/redirect';

  const link = document.getElementById('login');
  link.href = `${authorize_uri}?client_id=${client_id}&redirect_uri=${redirect_uri}`;
</script>

</body>

</html>
```

`public/welcome.html`
```html
<!DOCTYPE html>
<html lang="en">

<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>Hello</title>
</head>

<body>
</body>
<script>
const query = window.location.search.substring(1) || '';
const name = query.split('name=')[1];
document.body.appendChild(
  document.createTextNode(`Welcome, ${decodeURIComponent(name)}`)
);
</script>

</html>
```

`index.js`
```js
// Fill in your client ID and client secret that you obtained
// while registering the application
const clientID = '98ada85e5491d9b333ba'
const clientSecret = 'xxxx'

const Koa = require('koa');
const path = require('path');
const serve = require('koa-static');
const route = require('koa-route');
const axios = require('axios');

const app = new Koa();

const main = serve(path.join(__dirname + '/public'));

const oauth = async ctx => {
  const requestToken = ctx.request.query.code;
  console.log('authorization code:', requestToken);

  const tokenResponse = await axios({
    method: 'post',
    url: 'https://github.com/login/oauth/access_token?' +
      `client_id=${clientID}&` +
      `client_secret=${clientSecret}&` +
      `code=${requestToken}`,
    headers: {
      accept: 'application/json'
    }
  });

  const accessToken = tokenResponse.data.access_token;
  console.log(`access token: ${accessToken}`);

  const result = await axios({
    method: 'get',
    url: `https://api.github.com/user`,
    headers: {
      accept: 'application/json',
      Authorization: `token ${accessToken}`
    }
  });
  console.log(result.data);
  const name = result.data.name;

  ctx.response.redirect(`/welcome.html?name=${name}`);
};

app.use(main);
app.use(route.get('/oauth/redirect', oauth));

app.listen(8080);

console.log('listening......')
```

`package.json`
```json
{
  "name": "node-oauth-demo",
  "version": "1.0.0",
  "description": "a demo of OAuth2.0 using node",
  "main": "index.js",
  "scripts": {
    "start": "node index.js"
  },
  "license": "MIT",
  "dependencies": {
    "axios": "^0.18.0",
    "koa": "^2.7.0",
    "koa-route": "^3.2.0",
    "koa-static": "^5.0.0"
  }
}
```

modify the config.

- `index.js`: replace the values of the `clientID` and `clientSecret` variables.
- `public/index.html`: replace the values of the `client_id` variable.

install the dependencies.

```bash
$ pnpm i
```

## Step three: run the server

Now, run the server.

```bash
$ node index.js
```

Visit http://localhost:8080 in your browser, and click the link to login GitHub.

## reference
- https://github.com/ruanyf/node-oauth-demo