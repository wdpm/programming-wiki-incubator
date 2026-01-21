import React, {Suspense} from "react";
import ErrorBoundary from "./ErrorBoundary";
import {GridLoader} from "react-spinners";

const loadStatus = () => {
  console.log("load status");
  throw new Promise(resolves => setTimeout(resolves, 3000));
};

function Status() {
  const status = loadStatus();
  return <h1>status: {status}</h1>;
}

export default function App() {
  return (
      <Suspense fallback={<GridLoader/>}>
        <ErrorBoundary>
          <Status/>
        </ErrorBoundary>
      </Suspense>
  );
}

// 一开始，status里面的promise会pengding，因此被suspense感知，于是显示loader
// 3秒后，promise resolve，suspense 感知，Status()再次调用渲染，此时又回到之前的循坏等待3秒......

// 永远不会停止，这是死循坏。如何修复呢