let range1 = 255;
Array(range1).fill(0)
    // i from 0 to 254
    .map((_, i) => String.fromCharCode(i))
    .map(encodeURI)
    .map((v) => {
        console.log(v)
    })
