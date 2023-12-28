import { useState, createContext, useEffect } from "react";
import axios from "axios";
import { Redirect } from "react-router-dom";

const UserContext = createContext();

const UserProvider = ({ children }) => {

  const [state, setState] = useState({
    user: {},
    token: ""
  });

  useEffect(() =>{
    setState(JSON.parse(window.localStorage.getItem('auth')));
  }, []);

  const token = state && state.token ? state.token : "";
  axios.defaults.baseURL = process.env.REACT_APP_API_URL ;
  axios.defaults.headers.common["Authorization"] = `Bearer ${token}`;

  axios.interceptors.response.use(
    function(response){
      return response;
    },
    function(error){
      let res = error.response;
      if(res.status === 401 && res.config && !res.config.__isRetryRequest){
        setState(null);
        window.localStorage.removeItem("auth");
        return (
          <Redirect to="/login" />
        )
      }
    }
  )

  return (
    <UserContext.Provider value={[state, setState]}>
      {children}  
    </UserContext.Provider>
  );
  
};

export { UserContext, UserProvider };
