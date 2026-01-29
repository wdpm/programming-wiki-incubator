const {JSEND_STATUS, JSendFail, JSendError} = require('./jsend_exception');

/**
 * Objection of JSend Object
 * @private
 */
class JSendResponse {
    /**
     *  construct JSend object
     * @param {number} htttpStatus, HTTP STATUS Code
     * @param {string} status JSend Status
     * @param {number} code error code or status code, which will read applications.
     * @param {string} message dmessage, which will read user of application,
     * @param {*} data data, which will read application, usualy return value of REST API
     */
    constructor(htttpStatus, status, code, message, data) {
        this. _httpStatus = htttpStatus;
        this._status = status;
        this._code=code;
        this._message = message;
        this._data = data;
    }


    /**
     * get http satus
     */
    getHttpStatus() {
        return this._httpStatus;
    }
    /**
     * to Jsend response type
     */
    toJSendResponse() {
        return {
            status: this._status,
            code: this._code,
            message: this._message,
            data: this._data
        };
    }
}

/**
 * Objecttion of Success response.
 * @private
 */
class JSendSuccessResponse extends JSendResponse{
    constructor(data) {
        super(200, JSEND_STATUS.SUCCESS, undefined, undefined, data);
    }
}

/**
 * Objection of Fail response
 * @private
 */
class JSendFailResponse extends JSendResponse{
    constructor(httpStatus, fail){
        super(httpStatus, fail.status, fail.code, fail.message, fail.data);
        if(!(fail instanceof JSendFail)) {
            throw new Error('UNEXPECTED: JSendFailResponse must be created by JSendFail');
        }

    }
}

/**
 * Objection of Error Response.
 * @private
 */
class JSendErrorResponse extends JSendResponse {
    constructor(httpStatus, error){
        super(httpStatus, error.status, error.code, error.message, error.data);
        if(!(error instanceof JSendError)) {
            throw new Error('UNEXPECTED: JSendErrorResponse must be created by JSendError');
        }
    }
}

class ApplicationErrorResponse extends JSendResponse {
    constructor(error) {
        super(500, JSEND_STATUS.ERROR, error.code, error.message, JSON.parse(JSON.stringify(error)));
    }
}

/**
 * Entry points of this module
 */
module.exports = {
    JSEND_STATUS: JSEND_STATUS,
    JSendSuccessResponse: JSendSuccessResponse,
    JSendFailResponse: JSendFailResponse,
    JSendErrorResponse: JSendErrorResponse,
    ApplicationErrorReponse: ApplicationErrorResponse,
};
