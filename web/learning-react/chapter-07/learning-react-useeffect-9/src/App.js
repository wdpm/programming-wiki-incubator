import React, { useEffect, useState, useMemo } from "react";

const useAnyKeyToRender = () => {
  const [, forceRender] = useState();

  useEffect(() => {
    window.addEventListener("keydown", forceRender);
    return () => window.removeEventListener("keydown", forceRender);
  }, []);
};

function WordCount({ children = "" }) {
  useAnyKeyToRender();

  // const fn = () => {
  //   console.log("hello");
  //   console.log("world");
  // };

  const fn = useCallback(() => {
    console.log("hello");
    console.log("world");
  }, []);

  useEffect(() => {
    console.log("fresh render");
    fn();
  }, [fn]);

  return <p>{children}</p>;
}

export default function App() {
  return <WordCount>You are not going to believe this but...</WordCount>;
}
