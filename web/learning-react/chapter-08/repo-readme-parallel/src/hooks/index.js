import {useState, useEffect, useCallback, useMemo} from "react";

export function useFetch(uri) {
  const [data, setData] = useState();
  const [error, setError] = useState();
  const [loading, setLoading] = useState(true);

  // const mounted = useMountedRef();

  useEffect(() => {
    if (!uri) return;
    // 如果依赖的组件已被卸载，那就没有以后了
    // if (!mounted.current) return;
    fetch(uri)
        .then(data => {
          // 在我进行网络请求的期间，组件就无了，那也可以提前跑路了，会被catch捕获
          // if (!mounted.current) throw new Error("component is not mounted");
          return data;
        })
        .then(data => data.json())
        .then(setData)
        .then(() => setLoading(false))
        .catch(error => {
          // 再次检测组件存在性，不存在就返回
          // if (!mounted.current) return;
          setError(error)

          // 这里也应该清空loading状态的
          setLoading(false)
        });
  }, [uri]);

  return {
    loading,
    data,
    error
  };
}

export const useIterator = (items = [], initialValue = 0) => {
  const [i, setIndex] = useState(initialValue);

  const prev = useCallback(() => {
    if (i === 0) return setIndex(items.length - 1);
    setIndex(i - 1);
  }, [i]);

  const next = useCallback(() => {
    if (i === items.length - 1) return setIndex(0);
    setIndex(i + 1);
  }, [i]);

  console.log('i: ', i)
  const item = useMemo(() => items[i], [i]);

  return [item || items[0], prev, next];
};
