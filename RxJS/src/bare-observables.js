/**
 * Simple observable
 *
 * Created by evan on 2020/11/19
 * See <<RxJS in action> book page number 56
 */
const observable = events => {
    const INTERVAL = 1 * 1000;
    let schedulerId;
    return {
        subscribe: observer => {
            schedulerId = setInterval(() => {
                if (events.length === 0) {
                    observer.complete();
                    clearInterval(schedulerId);
                    schedulerId = undefined;
                } else {
                    observer.next(events.shift());
                }
            }, INTERVAL);
            return {
                unsubscribe: () => {
                    if (schedulerId) {
                        clearInterval(schedulerId);
                    }
                }
            };
        }
    }
};

let sub = observable([1, 2, 3])
    .subscribe({
        next: console.log,
        complete: () => console.log('Done!')
    });
