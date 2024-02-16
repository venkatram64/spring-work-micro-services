import { useState, useContext } from "react";
import axios from "axios";
import { toast } from "react-toastify";
import { Modal } from "antd";
import { Link } from "react-router-dom";
import { UserContext } from "../../context";
import { Redirect } from "react-router-dom";
import { SyncOutlined } from "@ant-design/icons";

const ForgotPassword = () => {

  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");
  const [ok, setOk] = useState(false);
  const [loading, setLoading] = useState(false);
  const [state, setState] = useContext(UserContext);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      setLoading(true);

      //axios is configured in UserRoute.js
      const { data } = await axios.put("/api/auth/forgot-password", {
        email: email,
        password: password,
      });

      //empty the form     
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
          <h2>Forgot Password</h2>
        </div>
      </div>

      {/* {loading ? <h1>Loading...</h1> : ''} */}

      <div className="row py-5">
        <div className="col-md-6 offset-md-3">
          <form onSubmit={handleSubmit}>
            <div className="form-group p-2">
              <small>
                <label className="text-muted">Your email</label>
              </small>
              <input
                value={email}
                onChange={(e) => setEmail(e.target.value)}
                type="email"
                className="form-control"
                placeholder="Enter email"
              />
            </div>
            <div className="form-group p-2">
              <small>
                <label className="text-muted">Enter new password</label>
              </small>
              <input
                value={password}
                onChange={(e) => setPassword(e.target.value)}
                type="password"
                className="form-control"
                placeholder="Enter password"
              />
            </div>
            <div className="form-group p-2">
              <button
                disabled={!email || !password}
                className="btn btn-primary col-12"
              >
                {loading ? <SyncOutlined spin className="py-1" /> : "Submit"}
              </button>
            </div>
          </form>
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
            <p>You have successfully updated password.</p>
            <Link to="/login">Login</Link>
          </Modal>
        </div>
      </div>
    </div>
  );
};

export default ForgotPassword;
