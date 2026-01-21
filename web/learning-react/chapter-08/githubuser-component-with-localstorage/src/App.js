import React, {useState, useEffect} from "react";

const loadJSON = key => key && JSON.parse(localStorage.getItem(key));
const saveJSON = (key, data) => localStorage.setItem(key, JSON.stringify(data));

function GitHubUser({login}) {
  const [data, setData] = useState(loadJSON(`user:${login}`));

  // data changed, need to update db
  useEffect(() => {
    if (!data) return;
    // "MoonHighway" === moonhighway ? 这代码多少有点诡异
    if (data.login === login) return;
    const {name, avatar_url, location} = data;
    saveJSON(`user:${login}`, {
      name,
      login,
      avatar_url,
      location
    });
  }, [data]);

  // login changed, need to request new data
  useEffect(() => {
    if (!login) return;
    if (data && data.login === login) return;
    fetch(`https://api.github.com/users/${login}`)
        .then(response => response.json())
        .then(setData)
        .catch(console.error);
  }, [login]);

  if (data) return <pre>{JSON.stringify(data, null, 2)}</pre>;

  return null;
}

export default function App() {
  return <GitHubUser login="moonhighway"/>;
}

// 第一次：显示完整的json
// data is null(skip first effect), do request ,then setData with complete data
// data is changed, then trigger data effect, (data.login === login) return;

// 第二次：显示精简的json
// data is not null. setData with partial data