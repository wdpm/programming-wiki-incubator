/**
 *  Jsend status
 * @private
 */
const JSEND_STATUS = {
    SUCCESS: 'success',
    FAIL: 'fail',
    ERROR: 'error',
    UNKNOWN: 'unknown'
};



/**
 *  Jsend Exception Base class
 *  @private
 */
class JSendException extends Error {

    /**
     *  base constructor set for each  properties 
     * @param {JSEND_STATUS} status  status fail or error
     * @param {number} code  code for error
     * @param {string} message message on error
     * @param {object} data data for fail
     */
    constructor(status, code, message, data) {

        // set the message
        super(message);

        // set the status, 
        this.status = status;
        if(JSEND_STATUS[status] === undefined) {
            this.status = status;
        }

        // set the code
        this.code = code;

        // set the data
        this.data = data;
    }
}

/**
 * Exception for JSend Fail message.
 * If you want to raise JSend fail. you must throw instance of this class.
 * @public
 */
class JSendFail extends JSendException {
    constructor(data) {
        super(JSEND_STATUS.FAIL, undefined, undefined, data);
    }
}

/**
 * Exception for JSend Error message.
 * If you want to raise JSend error. you must throw insatance of this class.
 */
class JSendError extends JSendException {

    constructor(code, message, data) {
        super(JSEND_STATUS.ERROR, code, message, data);
    }

}
module.exports = {
    JSendError: JSendError,
    JSendFail: JSendFail,
    JSEND_STATUS: JSEND_STATUS
}