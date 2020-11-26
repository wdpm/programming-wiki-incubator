button.addEventListener('click', () => {
    Promise.all(ajax('/source1'), ajax('/source2'))
        .then(([result1, result2]) => {
            processResults(result1, result2);
        });
});