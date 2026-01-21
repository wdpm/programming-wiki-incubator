import React, { useState, Suspense, lazy } from "react";
import Agreement from "./Agreement";
import ClimbingBoxLoader from "react-spinners/ClimbingBoxLoader";

// The issue is that all code for the Main component and all of its children is pack‐
// aged into a single JavaScript file: the bundle. That means that users have to wait for
// this codebase to download completely before the Agreement component is initially rendered
// const Main = () => import("./Main");

// use lazy()
// We can put off loading the main component until it has rendered by declaring it
// using React.lazy instead of initially importing it
const Main = lazy(() => import("./Main"));

export default function App() {
  const [agree, setAgree] = useState(false);

  if (!agree) return <Agreement onAgree={() => setAgree(true)} />;

  // return <Main />;

  // Instead of falling back to an error message when an error occurs,
  // the Suspense component renders a loading message when lazy loading occurs.

  // actually, commonly use skeleton screen to show loading state
  return (
    <Suspense fallback={<ClimbingBoxLoader />}>
      <Main />
    </Suspense>
  );

  // 1.We have a solution for pending: the Suspense component will render a loader animation while the request for the source code is pending.
  // 2.We have a solution for the failed state: if an error occurs while loading the Main component, it will be
  // caught and handled by the ErrorBoundary
  // 3.We even have a solution for success: if the request is successful, we’ll render the Main component

  // <ErrorBoundary fallback={ErrorScreen}>
  // <Suspense fallback={<ClimbingBoxLoader />}>
  // <Main />
  // </Suspense>
  // </ErrorBoundary>
}
