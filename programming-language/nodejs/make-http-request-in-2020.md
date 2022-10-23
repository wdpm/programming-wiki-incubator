# make http request in 2020

- ~~XMLHttpRequest~~
  > Fetch replaces it natively
- ~~JQuery.ajax~~
  > you don't need JQuery in 2020
- ~~Qwest~~
  > Why don't I use Fetch ?
- ~~SuperAgent~~
  > alternative is Axios
- ~~Http-client~~
  > compose HTTP clients using JavaScript's fetch API.
- Axios
  > Promise-based HTTP library for performing HTTP requests on both Browser and Nodejs
  - Support for upload progress
  - Can set a response timeout
  - Axios has implemented the cancellable promise proposal
- Fetch
  > Fetch is a native browser API to make a request that replaces XMLHttpRequest.
  ```
  (async () => {
    
    const response = await fetch('http://dataserver/update', {
      method: 'POST',
      headers: {
        'Accept': 'application/json',
        'Content-Type': 'application/json'
      },
      body: JSON.stringify({name:Murdock})
    });
  
    const result = await response.json();
    console.log(result);
  })();
  ```
- ~~Request~~ it is deprecated
- got 
  > https://github.com/sindresorhus/got
