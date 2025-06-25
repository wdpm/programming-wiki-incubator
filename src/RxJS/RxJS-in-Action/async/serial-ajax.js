button.addEventListener('click', () => {
    ajax('/source1', result1 => {
        ajax('/source2', result2 => {
            processResults(result1, result2);
        });
    });
});