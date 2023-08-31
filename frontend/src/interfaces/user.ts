enum Gender {
  MALE = "MALE",
  FEMALE = "FEMALE",
  OTHER = "OTHER",
}

interface User {
  userId: number;
  username: string;
  dob: [number, number, number];
  profileUrl: string;
  gender: Gender;
  role: "USER" | "ADMIN";
}

export interface UserWithStats extends User {
  followedCount: number;
  followerCount: number;
}

export default User;
