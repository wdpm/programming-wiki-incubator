function fastFunction(done) {
    setTimeout(function () {
        done(null, "fast")
    }, 100)
}

function slowFunction(done) {
    setTimeout(function () {
        done(null, "slow")
    }, 300)
}

function runSequentially(callback) {
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