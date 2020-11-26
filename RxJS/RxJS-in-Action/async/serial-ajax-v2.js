button.addEventListener('click', () => {
    let firstResult;
    ajax('/source1', result => {
        if (firstResult) processResults(result, firstResult);
        else firstResult = result;
    });
    ajax('/source2', result => {
        if (firstResult) processResults(firstResult, result);
        else firstResult = result;
    });
});