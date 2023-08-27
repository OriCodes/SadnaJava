import Post from "./post";
import User from "./user";

interface UserProfile {
  user: User;
  posts: Post[];
  page: number;
}

export default UserProfile;
