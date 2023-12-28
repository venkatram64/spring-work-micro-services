import { useEffect, useState, useContext } from "react";
import axios from "axios";
import { Redirect } from "react-router-dom";

import { SyncOutlined } from "@ant-design/icons";
import { UserContext } from "../../context";

const UserRoute = ({ children }) => {

  const [ok, setOk] = useState(false);
  const [state] = useContext(UserContext);

  useEffect(() => {
    if(state && state.token){
      getCurrentUser();
    }    
  }, [state && state.token]);

  const getCurrentUser = async () => {
    try {      
      // const user = await axios.get(
      //   `${process.env.REACT_APP_API_URL}/api/users`, {
      //     headers: {
      //       Authorization: `Bearer ${state.token}`
      //     }
      //   }
      // );

      //in UserRoute.js axios is configured
      const user = await axios.get('/api/users');
      
      if (user != null) {
        console.log("user is valid ", user.data);
        setOk(true);
      }

    } catch (err) {
      setOk(false);
      console.log("I am redirecting to login...");
      //<Redirect to="/login" />;
      //return <Redirect to={{pathname: "/login"}} />  
    }  
    return   
  };

  state == null && setTimeout(() =>{
    getCurrentUser();     
  }, 1000);

  return !ok ? (
    <SyncOutlined
      spin
      className="d-flux justify-content-center display-1 text-primary p-5"
    />
  ) : (
    <>{ children }</>
  );

  // if (!ok) {
  //   return (
  //     <SyncOutlined
  //       spin
  //       className="d-flux justify-content-center display-1 text-primary p-5"
  //     />
  //   );
  // }

  // // If the user is not authenticated, redirect to the login page
  // if (state === null && !ok) {
  //   return <Redirect to="/login" />;
  // }

  // return <>{children}</>;

};

export default UserRoute;
