enum Gender {
  MALE = "MALE",
  FEMALE = "FEMALE",
  OTHER = "OTHER",
}

interface User {
  userId: number;
  username: string;
  dob: string;
  profileUrl: string;
  gender: Gender;
  auth0Id: string;
}

export interface UserWithStats extends User {
  followedCount: number;
  followerCount: number;
}

export default User;
