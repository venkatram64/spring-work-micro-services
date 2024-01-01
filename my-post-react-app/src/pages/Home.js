import { useContext } from "react";
import { UserContext } from "../context";

const Home = () => {

  const [state, setState] = useContext(UserContext);

  return (
    <div className="container">
      <p>Home page</p>
      {/* {JSON.stringify(state)} */}
    </div>
  );
};

export default Home;
