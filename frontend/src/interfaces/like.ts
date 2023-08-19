import Post from "./post";
import User from "./user";

interface PostLike {
  postLikeId: number;
  user: User;
  post: Post;
  likeTime: string;
}

export default PostLike;
