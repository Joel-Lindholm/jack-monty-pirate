import http from './http.js'

export default {
  getJackMontyResult(islands, excluded) {
    return http.get("jack-monty?islands=" + islands + "&excluded=" + excluded);
  },
}
