const ajax = function (url) {
    return new Promise(function (resolve, reject) {
        let req = new XMLHttpRequest();
        req.responseType = 'json';
        req.open('GET', url);
        req.onload = function () {
            if (req.status === 200) {
                let data = JSON.parse(req.responseText);
                resolve(data);
            } else {
                reject(new Error(req.statusText));
            }
        };
        req.onerror = function () {
            reject(new Error('IO Error'));
        };
        req.send();
    });
};

ajax('/data')
    .then(()=>{
        // do something
    })
    .catch(error => console.log(`Error fetching data: ${error.message}`))