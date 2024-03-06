import { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import { VerticalLayout } from "@vaadin/react-components";

export const config: ViewConfig = {
    rolesAllowed: ["ADMIN"],
};

export default function AdminOnlyView() {
  return (
    <VerticalLayout theme="padding">
      <h3>Admin only view</h3>
      <p>This view is only visible to users with the 'ADMIN' role.</p>
    </VerticalLayout>
  );
}
