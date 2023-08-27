import { useCurrentUser } from "@/hooks/useUser";
import { Image } from "@chakra-ui/react";
import { FunctionComponent } from "react";

interface UserIconProps {}

const UserIcon: FunctionComponent<UserIconProps> = () => {
  const { user, isLoading, isError } = useCurrentUser();

  const profileUrl = user?.profileUrl;

  if (isLoading) {
    return null;
  }

  if (isError) {
    return null;
  }

  return (
    <Image
      src={profileUrl || "assets/svg/TALKSphere.svg"}
      borderRadius="50%"
      width="40px"
      height="40px"
    />
  );
};

export default UserIcon;
