// store.js
import { createStore } from 'vuex';
import {jwtDecode} from 'jwt-decode';

export default createStore({
    state: {
        userToken: null,
        userInfo:{
            email: null,
            name: null,
            surname: null,
            picture: null,
        },
    },
    mutations: {
        setUserToken(state, token) {
            state.userToken = token;
        },
        setUserInfo(state, userInfo) {
            state.userInfo = {...userInfo}
        },
        clearUserInfo(state) {
            state.userToken = null;
            state.userInfo = { email: null, name: null, surname: null, picture: null };
        }
    },
    actions: {
        saveUserInfo({ commit }, token) {
            if (token) {
                commit('setUserToken', token);
                try {
                    const decoded = jwtDecode(token);
                    commit('setUserInfo', {
                        email: decoded.sub,
                        name: decoded.name,
                        surname: decoded.surname,
                        picture: decoded.picture,
                    });
                } catch (e) {
                    console.error('Error decoding token: ', e);
                    commit('clearUserInfo');
                }
            } else {
                console.error('No token provided');
                commit('clearUserInfo');
            }
        },
        logoutUser({ commit }) {
            commit('clearUserInfo');
        }
    },
    getters: {
        isAuthenticated(state) {
            return !!state.userToken;
        },
        getUserToken(state) {
            return state.userToken;
        },
        getUserInfo(state) {
            return state.userInfo;
        },
    },
});
