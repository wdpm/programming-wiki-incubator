# Async best practices in 2020

## callback hell
```js
function fastFunction (done) {
  setTimeout(function () {
    done(null,"fast")
  }, 100)
}

function slowFunction (done) {
  setTimeout(function () {
    done(null,"slow")
  }, 300)
}
```
manually manage sequences
```js
function runSequentially (callback) {
  fastFunction((err, data) => {
    if (err) return callback(err)
    console.log(data)   // results of a
  
    slowFunction((err, data) => {
      if (err) return callback(err)
      console.log(data) // results of b
    })
  })
}

const errorHandler = (err) => console.log(err)
runSequentially(errorHandler)
```

## promise
```js
function fastFunction () {
  return new Promise((resolve, reject) => {
    setTimeout(function () {
      console.log('Fast function done')
      resolve()
    }, 100)
  })
}

function slowFunction () {
  return new Promise((resolve, reject) => {
    setTimeout(function () {
      console.log('Slow function done')
      resolve()
    }, 300)
  })
}

function asyncRunner () {
    return Promise.all([slowFunction(), fastFunction()])
}

asyncRunner()
    .then(([slowResult, fastResult]) => {
        console.log('All operations resolved successfully')
    })
    .catch((error) => {
        console.error('There has been an error:', error)
    })
```
## async
```js
// validateParams, dbQuery, serviceCall are thunks
async function handler () {
  try {
    await validateParams()
    const dbResults = await dbQuery()
    const serviceResults = await serviceCall()
    return { dbResults, serviceResults }
  } catch (error) {
    console.log(error)
  }
}
```