import React, { useState, useEffect ,useMemo} from "react";

const useAnyKeyToRender = () => {
  const [, forceRender] = useState();

  useEffect(() => {
    window.addEventListener("keydown", forceRender);
    return () => window.removeEventListener("keydown", forceRender);
  }, []);
};

function WordCount({ children = "" }) {
  useAnyKeyToRender();

  // const words = children.split(" ");
   const words = useMemo(() => children.split(" "), [children]);
  // useCallback can be used like useMemo, but it memoizes functions instead of values

  // 思考：为何随意按下按键，理论上words没有改变，为何也会打印呢？
  // 在 WordCount 函数内部，js会假设每次渲染时words都是不一样的
  useEffect(() => {
    console.log("fresh render");
  }, [words]);

  return (
    <>
      <p>{children}</p>
      <p>
        <strong>{words.length} - words</strong>
      </p>
    </>
  );
}

export default function App() {
  return <WordCount>You are not going to believe this but...</WordCount>;
}
