import vitePluginFileSystemRouter from '@vaadin/hilla-file-router/vite-plugin.js';
import { UserConfigFn } from 'vite';
import { overrideVaadinConfig } from './vite.generated.js';

const customConfig: UserConfigFn = (env) => ({
  // Here you can add custom Vite parameters
  // https://vitejs.dev/config/
  plugins: [vitePluginFileSystemRouter()]
});

export default overrideVaadinConfig(customConfig);
