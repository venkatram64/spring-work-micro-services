import { SyncOutlined } from "@ant-design/icons";

const AuthForm = ({
  handleSubmit,
  firstName,
  setFirstName,
  lastName,
  setLastName,
  email,
  setEmail,
  password,
  setPassword,
  loading,
  page
}) => (
  <form onSubmit={handleSubmit}>
    {page !== "login" && (
      <div className="form-group p-2">
        <small>
          <label className="text-muted">Your first name</label>
        </small>
        <input
          value={firstName}
          onChange={(e) => setFirstName(e.target.value)}
          type="text"
          className="form-control"
          placeholder="Enter first name"
        />
      </div>
    )}
    {page !== "login" && (
      <div className="form-group p-2">
        <small>
          <label className="text-muted">Your last name</label>
        </small>
        <input
          value={lastName}
          onChange={(e) => setLastName(e.target.value)}
          type="text"
          className="form-control"
          placeholder="Enter last name"
        />
      </div>
    )}
    {(page === "login" || page === "register") && (
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
    )}
    {(page === "login" || page === "register") && (
      <div className="form-group p-2">
        <small>
          <label className="text-muted">Your password</label>
        </small>
        <input
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          type="password"
          className="form-control"
          placeholder="Enter password"
        />
      </div>
    )}
    <div className="form-group p-2">
      <button
        disabled={
          page === "login"
            ? !email || !password
            : !firstName || !lastName || !email || !password
        }
        className="btn btn-primary col-12"
      >
        {loading ? <SyncOutlined spin className="py-1" /> : "Submit"}
      </button>
    </div>
  </form>
);

export default AuthForm;
