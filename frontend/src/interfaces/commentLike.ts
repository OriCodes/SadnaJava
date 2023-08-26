import Comment from "./comment";
import User from "./user";

interface CommentLike {
  commentLikeId: number;
  user: User;
  comment: Comment;
  likeTime: Date;
}

export default CommentLike;
