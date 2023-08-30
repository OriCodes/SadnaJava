import Post from "./post";
import { UserWithStats } from "./user";

interface UserProfile {
  user: UserWithStats;
  pagePosts: Post[];
  page: number;
}

export default UserProfile;
