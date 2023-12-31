import ReactQuill from "react-quill";
import 'react-quill/dist/quill.snow.css';

const PostForm = ({
  title,
  setTitle,
  content,
  setContent,
  postSubmit,
}) => {
  return (
    <div className="card">
      <div className="card-body pb-1">
        <form className="form-group">
          <input
            value={title}
            onChange={(e) => setTitle(e.target.value)}
            className="form-control"
            type="text"
            placeholder="write title..."
          />
          {/* <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="form-control"
          >
            placeholder="write some text..."
          </textarea> */}
          <ReactQuill
            theme="snow"
            value={content}
            onChange={(e) => setContent(e)}
            className="form-control"
            placeholder="write some text..."
          />
        </form>
      </div>
      <div  className="card-footer">
        <button 
            disabled={!title || !content}
            onClick={postSubmit}className="btn btn-primary mt-1">Post</button>
      </div>
    </div>
  );
};

export default PostForm;
