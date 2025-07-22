import { useState, createContext, useEffect } from "react";
import axios from "axios";
import { Redirect } from "react-router-dom";
/**
 * This file is used to manage the state of the user at the application level.
 * When the user logs in, the state is updated with the user data and the token.
 * The state is stored in local storage.
 * The state is used to protect the routes, only allowing logged in users to access certain pages.
 * The context is also used to provide the user data and token to all the components that need it.
 * This file is used by the App.js file to wrap the entire application with the UserProvider.
 * The UserProvider is a state management component that wraps the children and provides the state to all the components.
 * The children are the components that are wrapped by the UserProvider.
 * The UserProvider is also used to update the state when the user logs in or logs out.
 * The state is stored in local storage and is used to protect the routes.
 * The state is also used to provide the user data and token to all the components that need it.
 * The state is updated when the user logs in or logs out.
 * The state is used to provide the user data and token to all the components that need it.
 * The state is used to protect the routes, only allowing logged in users to access certain pages.
 * The context is also used to provide the user data and token to all the components that need it.
 * The context is used to share the state between all the components.
 * The context is used to provide the user data and token to all the components that need it.
 * The context is used to protect the routes, only allowing logged in users to access certain pages.
 * The context is used to share the state between all the components.
 * The context is used to provide the user data and token to all the components that need it.
 * The context is used to protect the routes, only allowing logged in users to access certain pages.
 */
//create a contenx object
const UserContext = createContext();
//this is a state management
const UserProvider = ({ children }) => {

  const [state, setState] = useState({
    user: {},
    token: ""
  });
  //from local storage update the state
  //which runs when the component mounts
  useEffect(() => {
    setState(JSON.parse(window.localStorage.getItem('auth')));
  }, []);

  const token = state && state.token ? state.token : "";
  //axios configuration settings
  axios.defaults.baseURL = process.env.REACT_APP_API_URL;
  axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

  axios.interceptors.response.use(
    function (response) {
      return response;
    },
    function (error) {
      console.log(error);
      debugger;
      let res = error.response;
      if (res.status === 401 && res.config) {
        setState(null);
        window.localStorage.removeItem("auth");
        return (
          <Redirect to="/login" />
        )
      }
      return Promise.reject(error);
    }
  )

  return (
    <UserContext.Provider value={[state, setState]}>
      {children}
    </UserContext.Provider>
  );

};

export { UserContext, UserProvider };
