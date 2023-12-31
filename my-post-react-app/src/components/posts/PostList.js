import {EditOutlined,DeleteOutlined} from '@ant-design/icons'
import { useContext } from 'react';
import { UserContext } from '../../context';
import { Link } from 'react-router-dom';
import { useHistory } from 'react-router-dom';

const PostList = ({posts, handleDelete}) => {
    const [state] = useContext(UserContext);
    const history = useHistory();

    return (
        <table className="py-2 table table-bordered table-striped table-hover">
            <thead>
                <tr>
                    <th>Post Title</th>
                    <th>Post Body</th>
                    <th>Edit</th>
                    <th>Delete</th>
                </tr>
            </thead>
            <tbody>
                {posts && posts.map((post) => <tr key={post.id}>
                                <th>{post.title}</th>
                                <th>{post.body}</th>
                                {state && state.user && state.user.id === post.userId && (
                                    <>
                                        <th><EditOutlined onClick={() => history.push(`/user/post/${post.id}`)} className='text-danger'/></th>
                                        <th><DeleteOutlined onClick={() => handleDelete(post)} className='text-danger'/></th>
                                    </>
                                )}
                                
                    </tr>)}
            </tbody>
        </table>
        
    )
}

export default PostList;