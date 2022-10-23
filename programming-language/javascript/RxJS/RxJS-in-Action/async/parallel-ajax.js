button.addEventListener('click', () => {
    let result1, result2 = {};
    ajax('/source1', data => {
        result1 = data;
    });
    ajax('/source2', data => {
        result2 = data;
    });
    setTimeout(() => {
        processResults(result1, result2);
    }, arbitraryWaitTimeMs);
    // Would need to be long enough so that both AJAX calls have enough time to finish
});