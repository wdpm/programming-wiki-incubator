const url = require('url');
const {
    JSendSuccessResponse,
    JSendErrorResponse,
    JSendFailResponse,
    ApplicationErrorReponse
} = require('./jsend_response');
const {JSendError, JSendFail} = require('./jsend_exception');

class JsonServerProc {
    constructor(conf) {
        this._conf = conf;
        this.API = {};
    }

    /**
     * Add api
     * @protected
     * @param {string} api entry point (pathname) of api.
     * @param {string} method http method. GET, DELETE, POST, PUT
     * @param {function} callback implementation of server function.
     */
    addAPI(api, method, callback) {
        if(this.API[api] == void 0) this.API[api] = {};
        this.API[api][method] = callback;
        return this;
    }

    //////////////////////////////////////////////////////////////////
    // [private] server main proc
    async proc(request, response) {
        var pathname = url.parse(request.url, true).pathname;
        var api = this.API[pathname];
        let responseObject = undefined;

        if(api != void 0) {
            if(typeof api[request.method] === 'function') {
                responseObject = await this._callAPI(api[request.method], request);
            } else {
                // method not allowed.
                responseObject = new JSendErrorResponse(405, new JSendError(405, 'method not allowed'));
            }
        } else {
            // 404 not found.
            if(this._conf.ignore404 != true) {
                responseObject = new JSendErrorResponse(404, new JSendError(404, 'method not allowed'));
            }
        }
        if(responseObject) {
            this._replyJson(response, responseObject);
        }
    }

    async _callAPI(fn, request) {
        let responseBody = undefined;

        try {
            // read args from request
            let args = await this._getArguments(request);
            try {
                responseBody = new JSendSuccessResponse(await fn(args));
            } catch(ex) {
                if(ex instanceof JSendFail) {
                    responseBody = new JSendFailResponse(200, ex);
                } else if (ex instanceof JSendError) {
                    responseBody = new JSendErrorResponse(200, ex);
                } else {
                    responseBody = new ApplicationErrorReponse(ex);
                }
            }
        } catch (_) {
            console.log(_);
            responseBody = new JSendErrorResponse(400, new JSendError(400, 'Bad Request'));
        }
        return responseBody;

    }

    //////////////////////////////////////////////////////////////////
    // [private] reply Json 
    _replyJson(response, reponseObject) {
        response.writeHead(reponseObject.getHttpStatus(), {'Content-Type': 'application/json; charset=utf-8', 'Access-Control-Allow-Origin': '*'});
        response.end(JSON.stringify(reponseObject.toJSendResponse()));
    }


    /**
     * Read arguments from URL or body.
     * *`GET` or `DELETE` : from URL
     * *`POST` or `PUT` : from BODY
     * @private
     * @param {IncommingMessage} request Incomming Message from client
     * @returns {object} argument of API
     */
    async _getArguments(request) {
        var ret = void 0;
        try {
            if(request.method == 'GET' || request.method == 'DELETE') {
                // if method GET or DELETE the arguments must be  in query string.
                ret = url.parse(request.url, true).query;
            }
            else if(request.method == 'POST' || request.method == 'PUT'){
                // if method POST or PUT then arguments must be in body of http request..
                ret = await this._readBodyJson(request);
            }    
        }
        catch(error) {
            // if error rethrow.
            throw error;
        }
        return ret;
    }

 
    /**
     * Read JSON from stream.
     * @private
     * @param {IncommingMessage} request Incomming message from HTTP client
     * @returns {Object} JSON from stream
     */
    _readBodyJson(request) {
        return new Promise((resolve, reject)=>{

            var content = '';

            // on reach data: concat chunk
            request.on('data', (chunk)=>{
                content += chunk;
                return content;
            });

            // on end: parse JSON and resolve this Promise
            request.on('end', ()=>{
                try {
                    resolve(JSON.parse(content));
                }
                catch(e) {
                    // on error syntax error
                    reject(new Error('request Json Systax Error.'));
                }
            });
        });
    }
}

module.exports = {
    JsonServerProc: JsonServerProc
}