import { useContext, useState, useEffect } from "react";
import { UserContext } from "../../context";
import UserRoute from "../../components/routes/UserRoute";
import PostForm from "../../components/forms/PostForm";
import { toast } from "react-toastify";
import axios from "axios";
import PostList from "../../components/posts/PostList";

const Dashboard = () => {
  const [state, setState] = useContext(UserContext);

  //state
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const [posts, setPosts] = useState([]);

  //which runs when the component mounts
  useEffect(() => {
    if (state && state.token && state.token.trim() !== "") fetchPosts();
  }, [state && state.token]);

  const fetchPosts = async () => {
    console.log("*****state is ", state);
    try {
      //axios configuration settings in index.js
      const { data } = await axios.get("/api/content/posts");
      setPosts(data);
      console.log("posts are ", data);
    } catch (err) {
      console.log(err);
    }
  };

  const postSubmit = async (e) => {
    e.preventDefault();
    console.log("post is ", title, content);
    console.log("state is ", state);
    try {
      const data = await axios.post("/api/content/posts", {
        userId: state.user.id,
        title: title,
        body: content,
      });
      if (data) {
        console.log("post data is ", data);
        toast.success("Post is created...");
        setTitle("");
        setContent("");
        fetchPosts();
      } else {
        toast.error("post not created");
      }
    } catch (err) {
      console.log(err);
      toast.error(err.message);
    }
  };

  const handleDelete = async (post) => {
    try {
      const answer = window.confirm("Are you sure to delete the post ?");
      if (!answer) return true;
      axios.delete(`/api/content/posts/${post.id}`);
      toast.success("Post is deleted.");
      fetchPosts();
    } catch (err) {
      console.log("Unable to delete the post, err");
    }
  };

  return (
    <UserRoute>
      <div className="container-fluid">
        <div className="row py-2">
          <div className="col">
            <h1 className="display-8 text-center">Posts</h1>
          </div>
        </div>
        <div className="row py-3">
          <div className="col-md-8">
            <PostForm
              title={title}
              setTitle={setTitle}
              content={content}
              setContent={setContent}
              postSubmit={postSubmit}
            />
          </div>
          <div className="col-md-4">Sidebar</div>
          {/* <pre>{JSON.stringify(posts, null, 4)}</pre> */}
          <div className="row py-3">
            <div className="col-md-8">{<PostList posts={posts} handleDelete={handleDelete} />}</div>
          </div>
        </div>
      </div>
    </UserRoute>
  );
};

export default Dashboard;
