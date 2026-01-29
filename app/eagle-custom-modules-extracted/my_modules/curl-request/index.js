module.exports = {
	post: async (url, params) => {
		return curl(url, "POST", params);
	},
	postJSON: async (url, params) => {
		return new Promise((resolve, reject) => {
			curl(url, "POST", params).then(function (result) {
				try {
					resolve(JSON.parse(result));
				}
				catch (err) {
					console.error(err)
					reject();
				}
			}).catch(() => {
				reject();	
			})
		});
	},
	get: async (url, params) => {
		return curl(url, "GET", params);
	},
	getJSON: async (url, params) => {
		return new Promise((resolve, reject) => {
			curl(url, "GET", params).then(function (result) {
				try {
					resolve(JSON.parse(result));
				}
				catch (err) {
					console.error(err)
					reject();
				}
			}).catch(() => {
				reject();	
			})
		});
	}
}

// var curlRequest = require(appRoot + '/my_modules/curl-request');
// console.log(await curlRequest.post("http://localhost:8080/check-trial", `-F 'machineID=5e83073c41101c5987c9ffcdc7b622e8b2b753d468a5a66922718c223f3e0b5f'`));

async function curl (url, type, params = "") {
    return new Promise((resolve, reject) => {

    	var hasReturn = false;
    	var timeout = 15000;

    	// 避免連線失敗或不回來卡死
    	setTimeout(function () {
    		if (!hasReturn) {
    			reject();
    		}
    	}, timeout);

        if (process.platform === 'darwin') {
            let exec = require('child_process').exec;
            let command = `curl -X ${type} '${url}' ${params}`;
            child = exec(command, function (error, stdout, stderr) {
            	hasReturn = true;
            	if (stdout) {
                	resolve(stdout);
                }
                else {
                	reject();
                }
            });
        }
        else {
            try {
                let spawn = require("child_process").spawn;
                let child = spawn("powershell.exe", [`Remove-item alias:curl; curl -s ${type} '${url}' ${params}`]);

                child.stdout.on("data", function(data) {
                	hasReturn = true;
                    if (data) {
                    	resolve(data.toString());
                    }
                    else {
                    	reject();
                    }
                });

                child.on("error", function(err) {
                	hasReturn = true;
                    reject();
                });

                child.stdin.end();
            }
            catch (err) {
            	hasReturn = true;
                reject();
            }
        }
    });
}