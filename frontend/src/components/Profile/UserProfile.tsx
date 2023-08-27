import { toNumber } from "lodash";
import { FunctionComponent } from "react";
import { useParams } from "react-router-dom";

const UserProfile: FunctionComponent<{}> = () => {
  const paramUserId = useParams<{ userId: string }>().userId;

  const userId = toNumber(paramUserId);

  return <div>UserProfile</div>;
};

export default UserProfile;
