import { useContext, useState } from "react";
import { UserContext } from "../../context";
import UserRoute from "../../components/routes/UserRoute";
import CreatePostForm from "../../components/forms/CreatePostForm";
import axios from "axios";

const Dashboard = () => {
  const [state, setState] = useContext(UserContext);

  //state
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const postSubmit = async (e) => {
    e.preventDefault();
    console.log("post is ", title, content);
    console.log("state is ", state);
    try {
      const { data } = await axios.post("/api/content/posts", {
        userId: state.user.id,
        title: title,
        body: content,
      });
      console.log("post data is ", data);
    } catch (err) {
      console.log(err);
    }
  };

  return (
    <UserRoute>
      <div className="container-fluid">
        <div className="row py-5">
          <div className="col">
            <h1 className="display-1 text-center">Posts</h1>
          </div>
        </div>
        <div className="row py-3">
          <div className="col-md-8">
            <CreatePostForm
              title={title}
              setTitle={setTitle}
              content={content}
              setContent={setContent}
              postSubmit={postSubmit}
            />
          </div>
          <div className="col-md-4">Sidebar</div>
        </div>
      </div>
    </UserRoute>
  );
};

export default Dashboard;
