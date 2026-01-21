import React from "react";
import {useIterator} from "./hooks";

export default function RepoMenu({repositories, login, selectedRepo, onSelect}) {

  // 注意，默认是30条一页，可能index为-1，找不到。绝对不能提供-1
  let findIndex = repositories.findIndex(repo => repo.name === selectedRepo);
  const [{name}, previous, next] = useIterator(
      repositories,
      selectedRepo ? (findIndex === -1 ? 0 : findIndex) : 0
  );

  // 每次点击prev或者next后，i变更=> name就会变更
  onSelect(name)

  return (
      <>
        <div style={{display: "flex"}}>
          <button onClick={previous}>&lt;</button>
          <p>{name}</p>
          <button onClick={next}>&gt;</button>
        </div>
      </>
  );
}
