const CreatePostForm = ({
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
          <textarea
            value={content}
            onChange={(e) => setContent(e.target.value)}
            className="form-control"
          >
            placeholder="write some text..."
          </textarea>
        </form>
      </div>
      <div  onClick={postSubmit} className="card-footer">
        <button className="btn btn-primary mt-1">Post</button>
      </div>
    </div>
  );
};

export default CreatePostForm;
