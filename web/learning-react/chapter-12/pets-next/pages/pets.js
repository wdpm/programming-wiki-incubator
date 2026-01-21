import Layout from "./Layout";
import fetch from "isomorphic-unfetch";

export default function Pets(props) {
  return (
      <Layout>
        <div>
          <h1>Pets!</h1>
          <ul>
            {props.pets.map(pet => (
                <li key={pet.id}>{pet.name}</li>
            ))}
          </ul>
        </div>
      </Layout>
  );
}

Pets.getInitialProps = async function () {
  // const res = await fetch(
  //   `http://pet-library.moonhighway.com/api/pets`
  // );
  // const data = await res.json();

  const data = [
    {
      "id": 1,
      "name": "meow"
    },
    {
      "id": 2,
      "name": "meow meow"
    },
  ]

  return {
    pets: data
  };
};
