import {useParams} from "react-router";

export default function PersonView() {

  const { id } = useParams();

  return (
    <span>Details for person with Id = {id}</span>
  );
}