/**
 * This self-invoking function displays all the Flask flash
 * messages that are queued up.
 */
(function() {
    const option = {
        animation: true,
        delay: 3000
    }
    var toastElements = [].slice.call(document.querySelectorAll('.toast'))
    toastElements.map((toastElement) => {
        toast = new bootstrap.Toast(toastElement, option)
        // toast div 在macros中只是插入到DOM中，初始化为隐藏模式，需要js主动显示
        toast.show()
    })
}())
