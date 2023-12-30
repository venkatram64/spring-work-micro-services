import { useState, useContext } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { Link } from "react-router-dom";
import AuthForm from "../components/forms/AuthForm";
import { Redirect } from "react-router-dom";
import { UserContext } from "../context";

const Login = () => {
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [loading, setLoading] = useState(false);
  const [redirectTo, setRedirectTo] = useState(false);
  const [state, setState] = useContext(UserContext);

  const handleSubmit = async (e) => {
    e.preventDefault();
    //console.log(firstName, lastName, email, password);
    try {
      setLoading(true);
      // const { data } = await axios.post(
      //   `${process.env.REACT_APP_API_URL}/api/auth/authenticate`,
      //   {
      //     email: email,
      //     password: password,
      //   }
      // );

      //axios is configured in UserRoute.js
      const { data } = await axios.post("/api/auth/authenticate", {
        email: email,
        password: password,
      });
      //update context
      setState({
        user: data.user,
        token: data.token,
      });
      //store in localstorage
      window.localStorage.setItem("auth", JSON.stringify(data));
      //empty the form
      setEmail("");
      setPassword("");
      setLoading(false);
      setRedirectTo(true);
    } catch (err) {
      console.log("Error is from login page ", err);
      if (err.response.data.message) {
        toast.error(err.response.data.message);
      }
      setLoading(false);
    }
  };
  // if(state && state.token) {
  //    return <Redirect to="/" />
  // }
  return (
    <div className="container-fluid">
      {redirectTo ? <Redirect to="/" /> : ""}
      <div className="row py-5 bg-secondary text-light">
        <div className="col text-center">
          <h2>Login</h2>
        </div>
      </div>

      {/* {loading ? <h1>Loading...</h1> : ''} */}

      <div className="row py-5">
        <div className="col-md-6 offset-md-3">
          <AuthForm
            handleSubmit={handleSubmit}
            email={email}
            setEmail={setEmail}
            password={password}
            setPassword={setPassword}
            loading={loading}
            page="login"
          />
        </div>
      </div>
      <div className="row">
        <div className="col">
          <p className="text-center">
            Not yet registered?
            <Link to="/register"> Register</Link>
          </p>
        </div>
      </div>
      <div className="row">
        <div className="col">
          <p className="text-center">
            <Link to="/user/forgot-password" className="text-danger">
              Forgot Password
            </Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Login;
