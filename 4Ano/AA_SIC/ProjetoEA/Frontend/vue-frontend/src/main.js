import { createApp } from 'vue'
import router from './router'
import App from './App.vue'
import store from './store'

import { library } from "@fortawesome/fontawesome-svg-core";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { fas } from '@fortawesome/free-solid-svg-icons'
library.add(fas);
import { fab } from '@fortawesome/free-brands-svg-icons';
library.add(fab);
import { far } from '@fortawesome/free-regular-svg-icons';
library.add(far);
import { dom } from "@fortawesome/fontawesome-svg-core";
dom.watch();


library.add(fas);


createApp(App).use(router).use(store).component("font-awesome-icon", FontAwesomeIcon).mount('#app')



