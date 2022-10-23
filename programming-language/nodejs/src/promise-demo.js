function fastFunction() {
    return new Promise((resolve, reject) => {
        setTimeout(function () {
            console.log('Fast function done')
            resolve()
        }, 100)
    })
}

function slowFunction() {
    return new Promise((resolve, reject) => {
        setTimeout(function () {
            console.log('Slow function done')
            resolve()
        }, 300)
    })
}

function asyncRunner() {
    // Since node@12.9.0, there is a method called promise.allSettled,
    // that we can use to get the result of all the passed in promises regardless of rejections
    return Promise.all([slowFunction(), fastFunction()])
}

asyncRunner()
    .then(([slowResult, fastResult]) => {
        console.log('All operations resolved successfully')
    })
    .catch((error) => {
        console.error('There has been an error:', error)
    })