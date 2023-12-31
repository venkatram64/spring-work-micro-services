import { useState, useEffect, useContext } from "react";
import { useParams } from "react-router-dom";
import axios from "axios";
import PostForm from "../../components/forms/PostForm";
import UserRoute from "../../components/routes/UserRoute";
import { UserContext } from "../../context";
import { toast } from "react-toastify";
import { useHistory } from 'react-router-dom';

const EditPost = () => {
  const [state, setState] = useContext(UserContext);
  const params = useParams();
  const [post, setPost] = useState({});

  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");
  const history = useHistory();

  const postId = params.postId;

  useEffect(() => {
    console.log("param value  ", { postId });
    if (postId) {
      fetchPosts();
    }
  }, [params.postId]);

  const fetchPosts = async () => {
    try {
      const { data } = await axios.get(`/api/content/posts/${postId}`);
      console.log("posts...", data);
      setPost(data);
      setTitle(data.title);
      setContent(data.body);
    } catch (err) {
      console.log(err);
    }
  };

  const postSubmit = async (e) => {
    e.preventDefault();
    try {
      const { data } = await axios.put(`/api/content/posts`, {
        id: postId,
        userId: state.user.id,
        title: title,
        body: content,
      });
      console.log("updated post", title, content);
      toast.success("posts updation successful");
      history.push("/user/dashboard");
      
    } catch (err) {
      toast.error("posts updation failed");
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
          <div className="col-md-8 offset-md-2">
            <PostForm
              title={title}
              setTitle={setTitle}
              content={content}
              setContent={setContent}
              postSubmit={postSubmit}
            />
          </div>
        </div>
      </div>
    </UserRoute>
  );
};

export default EditPost;
