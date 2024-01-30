import { useEffect, useState, useContext } from "react";
import axios from "axios";

import { SyncOutlined } from "@ant-design/icons";
import { UserContext } from "../../context";
import { useHistory, Redirect } from "react-router-dom";

//this will protect the pages/routes
const UserRoute = ({ children }) => {
  const [ok, setOk] = useState(false);
  const [state] = useContext(UserContext);
  const history = useHistory();

  //below component will be execuated, when the component mounts
  useEffect(() => {
    if (state && state.token && state.token.trim() !== "") {
      getCurrentUser();
    }
  }, [state && state.token]);

  const getCurrentUser = async () => {
    //debugger;
    try {
      // const user = await axios.get(
      //   `${process.env.REACT_APP_API_URL}/api/users`, {
      //     headers: {
      //       Authorization: `Bearer ${state.token}`
      //     }
      //   }
      // );

      //in UserRoute.js axios is already configured, so use only endpoint not the entire url
      const user = await axios.get("/api/users");

      if (user != null) {
        console.log("user is valid ", user.data);
        setOk(true);
      } else {
        console.log("I am redirecting to login...");
        history.push("/login");
      }
    } catch (err) {
      setOk(false);
      console.log("I am redirecting to login because user is not logged in...");
      history.push("/login");
    }
    return;
  };

  state === null && setTimeout(() => {
    getCurrentUser();
  }, 1000);

  return !ok ? (
    <SyncOutlined
      spin
      className="d-flux justify-content-center display-1 text-primary p-5"
    />
  ) : (
    <>{children}</>
  );
};

export default UserRoute;
