import React, {useState} from "react";
import UserRepositories from "./UserRepositories";
import GitHubUser from "./GithubUser";
import RepositoryReadme from "./RepositoryReadme";


export default function App() {
  const [login, setLogin] = useState("moonhighway");
  const [repo, setRepo] = useState("learning-react");
  // think: set empty value when inits

  return (
      <>
        {/* then use {login && <GitHubUser login={login} />} guard*/}
        <GitHubUser login={login}/>
        <UserRepositories
            login={login}
            selectedRepo={repo}
            onSelect={setRepo}
        />
        <RepositoryReadme login={login} repo={repo}/>
      </>
  );
}
