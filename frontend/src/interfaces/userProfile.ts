import Post from "./post";
import User from "./user";

interface UserProfile {
  user: User;
  pagePosts: Post[];
  page: number;
}

export default UserProfile;
