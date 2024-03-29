import type { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button } from "@vaadin/react-components/Button.js";
import { TextField } from "@vaadin/react-components/TextField.js";
import { HelloEndpoint } from "Frontend/generated/endpoints.js";
import { useState } from "react";
import {VerticalLayout} from "@vaadin/react-components/VerticalLayout.js";

export default function HillaView() {
  const [name, setName] = useState("");
  const [notifications, setNotifications] = useState([] as string[]);

  return (
    <>
      <VerticalLayout theme="padding spacing">
        <h3>Hilla View</h3>
        <TextField
          label="Your name"
          onValueChanged={(e) => {
            setName(e.detail.value);
          }}
        />
        <Button
          onClick={async () => {
            const serverResponse = await HelloEndpoint.sayHello(name);
            setNotifications(notifications.concat(serverResponse));
          }}
        > Say hello </Button>
        {notifications.map((notification, index) => (
          <p key={index}>{notification}</p>
        ))}
      </VerticalLayout>
    </>
  );
}
