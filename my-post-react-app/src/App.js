import { BrowserRouter, Route, Switch } from "react-router-dom";
import { ToastContainer } from "react-toastify";
import "react-toastify/dist/ReactToastify.min.css";
import "antd/dist/reset.css";

import Home from "./pages/Home";
import Register from "./pages/Register";
import Login from "./pages/Login";
import Nav from "./components/Nav";
import { UserProvider } from "./context";
import Dashboard from "./pages/user/Dashboard";
import ForgotPassword from "./pages/user/ForgotPassword";

const App = () => {
  return (
    <UserProvider>
      <BrowserRouter>
        <ToastContainer position="top-center" />
        <Nav />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/login" component={Login} />
          <Route exact path="/register" component={Register} />
          <Route exact path="/user/dashboard" component={Dashboard} />
          <Route exact path="/user/forgot-password" component={ForgotPassword} />
        </Switch>
      </BrowserRouter>
    </UserProvider>
  );
};

export default App;
