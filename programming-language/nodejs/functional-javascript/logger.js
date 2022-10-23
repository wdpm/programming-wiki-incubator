function logger(namespace) {
    return console.log.bind(console, namespace)
}
let info = logger('INFO:')
info('this is an info message')