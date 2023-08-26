import useUser from "@/hooks/useUser";
import { FunctionComponent } from "react";

interface UserIconProps {}

const UserIcon: FunctionComponent<UserIconProps> = () => {
  const { user, isLoading, isError } = useUser();

  const profileUrl = user?.profileUrl;

  if (isLoading) {
    return null;
  }

  if (isError) {
    return null;
  }

  return (
    <img
      src={profileUrl || "assets/svg/TALKSphere.svg"}
      style={{ width: "40px", height: "40px", borderRadius: "50%" }}
    />
  );
};

export default UserIcon;
