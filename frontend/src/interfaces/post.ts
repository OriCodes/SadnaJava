import Comment from "./comment";
import PostLike from "./postLike";
import Topic from "./topic";
import User from "./user";

interface Post {
  postId: number;
  title: string;
  text: string;
  createdTimeStamp: string;
  user: User;
  topic: Topic;
  comments: Comment[];
  likes: PostLike[];
}

export default Post;
