import { router } from "Frontend/routes.js";
import { RouterProvider } from "react-router-dom";
import { configureAuth } from '@vaadin/hilla-react-auth';

export default function App() {
  // Needs to be replaced with the real auth server request
  const { AuthProvider, useAuth } = configureAuth(async () => ({ roles: ['admin'] }));

  return (
    <AuthProvider>
      <RouterProvider router={router} />
    </AuthProvider>
  );
}
