import axios from 'axios';
import Vue from 'vue';

import config from '@/config';

export default {
  install() {
    const baseUrl = config.API_ENDPOINT;
    Vue.prototype.$apiCaller = axios.create({
      baseURL: baseUrl,
      withCredentials: false,
      headers: {
        'Access-Control-Allow-Origin': '*',
        'Access-Control-Allow-Methods': 'GET,POST',
      },
    });
  },
};
