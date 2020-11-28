ajax('/data')
    .then(item => ajax(`/data/${item.getId()}/info`))
    .catch(error => console.log(`Error fetching data: ${error.message}`))
    .then(dataInfo => ajax(`/data/images/${dataInfo.img}`))
    .catch(error => console.log(`Error each data item: ${error.message}`))
    .then(showImg)
    .catch(error => console.log(`Error image: ${error.message}`))