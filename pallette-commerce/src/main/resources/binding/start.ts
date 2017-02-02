import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import { AccountModule } from './accountModule';
const platform = platformBrowserDynamic();
platform.bootstrapModule(AccountModule);
