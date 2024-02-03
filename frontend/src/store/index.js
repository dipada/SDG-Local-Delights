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
        shopId: null,
        cartProducts: [],
        // { id: 1, quantity: 2 },
        // { id: 2, quantity: 1 },
        // ... altri prodotti ...
    },
    mutations: {
        setShopId(state, shopId) {
            state.shopId = shopId;
        },

        setUserToken(state, token) {
            state.userToken = token;
        },
        setUserInfo(state, userInfo) {
            state.userInfo = {...userInfo}
        },
        clearUserInfo(state) {
            state.userToken = null;
            state.userInfo = { email: null, name: null, surname: null, picture: null };
        },

        addProductToCart(state, productId) {
            if (!state.cartProducts) {
                state.cartProducts = [];
            }

            const productExists = state.cartProducts.find(product => product.id === productId);
            if (!productExists) {
                state.cartProducts.push({ id: productId, quantity: 1 });
            } else {
                productExists.quantity++;
            }

            console.log('Cart product da store: ', state.cartProducts);
        },

        incrementProductQuantity(state, productId) {
            const product = state.cartProducts.find(p => p.id === productId);
            if (product) {
                product.quantity++;
            }
        },

        decrementProductQuantity(state, productId) {
            const product = state.cartProducts.find(p => p.id === productId);
            if (product && product.quantity > 1) {
                product.quantity--;
            }
        },

        removeProductFromCart(state, productId) {
            state.cartProducts = state.cartProducts.filter(p => p.id !== productId);
        }

    },
    actions: {
        addProductToCart({ commit }, productId) {
            commit('addProductToCart', productId);
        },

        removeProductFromCart({ commit }, productId) {
            commit('removeProductFromCart', productId);
        },

        saveShopId({ commit }, shopId) {
            commit('setShopId', shopId);
        },

        saveUserInfo({ commit }, token) {
            if (token) {
                localStorage.setItem('userToken', token); // XSS vulnerability, use httpOnly cookies in production
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
            localStorage.removeItem('userToken'); // XSS vulnerability, use httpOnly cookies in production
            commit('clearUserInfo');
        },

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
        getShopId(state) {
            return state.shopId;
        },

        //getCartProducts: (state) => {
        //    // Qui dovresti restituire i dettagli completi dei prodotti nel carrello,
        //    // per esempio combinando i dati dallo stato o da un'API se necessario.
        //    return state.cartProducts.map(product => {
        //        // Supponendo di avere un getter che restituisce tutti i prodotti
        //        const fullProduct = state.allProducts.find(p => p.id === product.id);
        //        return {
        //            ...fullProduct,
        //            quantity: product.quantity
        //        };
        //    });
        //},

        getCartProducts(state) {
            return state.cartProducts;
        },
    },
});
