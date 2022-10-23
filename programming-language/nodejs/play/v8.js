// Print GC events to stdout for one minute.
const v8 = require('v8');
v8.setFlagsFromString('--trace_gc');
setTimeout(function () {
    v8.setFlagsFromString('--notrace_gc');
}, 60 * 1000);