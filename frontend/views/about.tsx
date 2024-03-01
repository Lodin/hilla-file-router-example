import { VerticalLayout } from "@vaadin/react-components/VerticalLayout.js";
import { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import {useState} from "react";
import {Button} from "@vaadin/react-components/Button.js";
import {Link, useNavigate} from "react-router-dom";


export const config: ViewConfig = {
  title: "About Title",
  route: "a/bout",
  menu: {
    title: "About Menu Title",

  }
};

function generateRandomId() {
  return (Math.random()*10000).toFixed(0);
}

export default function AboutView() {

  const [id, setId] = useState<string | undefined>(undefined);
  const navigate = useNavigate();

  const handleGenerate = () => {
    setId(generateRandomId());
  };

  return (
    <VerticalLayout theme="padding">
      <p>This project is a simplified example on how to mix:</p>
      <ul>
        <li>Hilla with React</li>
        <li>Flow in pure Java</li>
      </ul>
      <p>Select person id to view details: <Button onClick={handleGenerate}>Generate Link</Button></p>
      {id && <Link to={`/person/${id}`}>(Navigate by link) See Person info for id = {id}</Link>}
      {id && <Button onClick={event => navigate(`/person/${id}`)}>(Navigate by navigate) See Person info for id = {id}</Button>}
    </VerticalLayout>
  );
}
