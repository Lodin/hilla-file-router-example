import { ViewConfig } from "@vaadin/hilla-file-router/types.js";
import { VerticalLayout } from "@vaadin/react-components";
import { useAuth } from "Frontend/util/auth";

export const config: ViewConfig = {
    rolesAllowed: ["USER", "ADMIN"],
};

export default function UsersOnlyView() {
    const { state } = useAuth();
    return (
        <VerticalLayout theme="padding">
            <h3>User only view</h3>
            <p>This view is only visible to authenticated users.</p>
            <p>Current user: <b>{state.user?.name}</b></p>
        </VerticalLayout>
    );
}
