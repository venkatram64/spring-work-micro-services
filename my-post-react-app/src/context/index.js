import { useState, createContext, useEffect } from "react";
import axios from "axios";
import { Redirect } from "react-router-dom";

//create a contenx object
const UserContext = createContext();

const UserProvider = ({ children }) => {

  const [state, setState] = useState({
    user: {},
    token: ""
  });
  //from local storage update the state
  //which runs when the component mounts
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
      //console.log(error);
      //debugger;
      let res = error.response;
      if(res.status === 401 && res.config){
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
