import { Link } from "react-router-dom";
import { useContext, useEffect, useState } from "react";
import { UserContext } from "../context";
import { Redirect, withRouter } from "react-router-dom";
import Dashboard from "../pages/user/Dashboard";

const Nav = (props) => {
  
  const [current, setCurrent] = useState("");
  const [state, setState] = useContext(UserContext);

  useEffect(() => {
    setCurrent(props.history.location.pathname);
  }, [props.history.location.pathname]);

  //console.log("Current is ", current);

  const logout = () => {
    window.localStorage.removeItem("auth");
    setState(null); //setting empty object
    props.history.push("/login");
  };

  const isActive = (path) => {
    if (props.history.location.pathname === path) {
      return { backgroundColor: "navy" };
    } else {
      return { color: "#ffffff" };
    }
  };

  return (
    <nav
      className="nav d-flex justify-content-between"
      style={{ backgroundColor: "blue" }}
    >
      <li className="nav-item">
        <Link className="nav-link text-light" to="/" style={isActive("/")}>
          Home
        </Link>
      </li>
      {state !== null ? (
        <>
          <li className="nav-item">
            <Link
              className="nav-link text-light"
              to="/user/dashboard"
              style={isActive("/user/dashboard")}
            >
              {state && state.user && state.user.firstName}
            </Link>
          </li>
          <li className="nav-item">
            <a
              onClick={logout}
              className="nav-link text-light"
              style={(isActive("/logout"), { cursor: "pointer" })}
            >
              Logout
            </a>
          </li>
        </>
      ) : (
        <>
          <li className="nav-item">
            <Link
              className="nav-link text-light"
              to="/login"
              style={isActive("/login")}
            >
              Login
            </Link>
          </li>
          <li className="nav-item">
            <Link
              className="nav-link text-light"
              to="/register"
              style={isActive("/register")}
            >
              Register
            </Link>
          </li>
        </>
      )}
    </nav>
  );
};

export default withRouter(Nav);
