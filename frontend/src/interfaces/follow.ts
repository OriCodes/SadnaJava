import User from "./user";

interface Follow {
  followId: number;
  follower: User;
  followed: User;
  followDate: number;
}

export default Follow;
