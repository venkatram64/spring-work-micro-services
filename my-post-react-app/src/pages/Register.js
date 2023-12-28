import { useState, useContext } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { Modal } from "antd";
import { Link } from "react-router-dom";
import AuthForm from "../components/forms/AuthForm";
import { UserContext } from "../context";
import { Redirect } from "react-router-dom";

const Register = () => {
  const [firstName, setFirstName] = useState("");
  const [lastName, setLastName] = useState("");
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [ok, setOk] = useState(false);
  const [loading, setLoading] = useState(false);
  const [state, setState] = useContext(UserContext);

  // const handleSubmit = (e) => {
  //   e.preventDefault();
  //   //console.log(firstName, lastName, email, password);
  //   axios
  //     .post(`${process.env.REACT_APP_API_URL}/api/auth/register`, {
  //       firstName: firstName,
  //       lastName: lastName,
  //       email: email,
  //       password: password,
  //     })
  //     .then((res) => {
  //       console.log(res);
  //       setOk(res != null ? true : false);
  //     })
  //     .catch((err) => {
  //       //console.log(err);
  //       //console.log(err.response.data.message);
  //       toast.error(err.response.data.message);
  //     });
  // };

  const handleSubmit = async (e) => {
    e.preventDefault();
    //console.log(firstName, lastName, email, password);
    try {
      setLoading(true);
      //const { data } = await axios.post(
      //   `${process.env.REACT_APP_API_URL}/api/auth/register`,
      //   {
      //     firstName: firstName,
      //     lastName: lastName,
      //     email: email,
      //     password: password,
      //   }
      // );

      //axios is configured in UserRoute.js
      const { data } = await axios.post("/api/auth/register", {
        firstName: firstName,
        lastName: lastName,
        email: email,
        password: password,
      });

      //empty the form
      setFirstName("");
      setLastName("");
      setEmail("");
      setPassword("");

      setOk(true);
      setLoading(false);
    } catch (err) {
      toast.error(err.response.data.message);
      setLoading(false);
    }
  };
  if (state && state.token) {
    return <Redirect to="/" />;
  }
  return (
    <div className="container-fluid">
      <div className="row py-5 bg-secondary text-light">
        <div className="col text-center">
          <h2>Register</h2>
        </div>
      </div>

      {/* {loading ? <h1>Loading...</h1> : ''} */}

      <div className="row py-5">
        <div className="col-md-6 offset-md-3">
          <AuthForm
            handleSubmit={handleSubmit}
            firstName={firstName}
            setFirstName={setFirstName}
            lastName={lastName}
            setLastName={setLastName}
            email={email}
            setEmail={setEmail}
            password={password}
            setPassword={setPassword}
            loading={loading}
            page="register"
          />
        </div>
      </div>
      <div className="row">
        <div className="col">
          <Modal
            title="Congratulation"
            open={ok}
            onCancel={() => setOk(false)}
            footer={null}
          >
            <p>You have successfully registerd.</p>
            <Link to="/login">Signin</Link>
          </Modal>
        </div>
      </div>
      <div className="row">
        <div className="col">
          <p className="text-center">
            Already registered?
            <Link to="/login"> Login</Link>
          </p>
        </div>
      </div>
    </div>
  );
};

export default Register;
