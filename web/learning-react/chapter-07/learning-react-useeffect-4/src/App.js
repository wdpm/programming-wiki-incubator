import React, {useState, useEffect} from "react";

export default function App() {
  const [val, set] = useState("");
  const [phrase, setPhrase] = useState("example phrase");

  const createPhrase = () => {
    setPhrase(val);
    set("");
  };

  useEffect(() => {
    console.log(`typing "${val}"`);
  });

  useEffect(() => {
    console.log(`saved phrase: "${phrase}"`);
  });

  // useEffect(() => {
  //   console.log(`typing "${val}"`);
  // }, [val]);
  //
  // useEffect(() => {
  //   console.log(`saved phrase: "${phrase}"`);
  // }, [phrase]);

  return (
      // 每次input的值改变时，都出发了所有的effects，控制粒度太粗了。
      <>
        <label>Favorite phrase:</label>
        <input
            value={val}
            placeholder={phrase}
            onChange={e => set(e.target.value)}
        />
        <button onClick={createPhrase}>send</button>
      </>
  );
}
