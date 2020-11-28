class Try {
    constructor(val) {
        this._val = val;
    }

    static of(fn) {
        try {
            return new Success(fn());
        } catch (error) {
            return new Failure(error);
        }
    }

    map(fn) {
        return Try.of(() => fn(this._val));
    }
}

class Success extends Try {
    getOrElse(anotherVal) {
        return this._val;
    }

    getOrElseThrow() {
        return this._val;
    }
}

class Failure extends Try {
    map(fn) {
        return this;
    }

    getOrElse(anotherVal) {
        return anotherVal;
    }

    getOrElseThrow() {
        if (this._val !== null) {
            throw this._val;
        }
    }
}