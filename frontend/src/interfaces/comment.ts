import Post from "./post";
import User from "./user";

interface Comment {
  commentId: number;
  text: string;
  createdTimeStamp: string;
  user: User;
  post: Post;
}

export default Comment;
