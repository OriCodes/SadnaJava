import Post from "@/interfaces/post";
import { Text } from "@chakra-ui/react";
import { formatDistance } from "date-fns";
import { FunctionComponent } from "react";

interface PostTimestampProps {
  post: Post;
}

const PostTimestamp: FunctionComponent<PostTimestampProps> = ({ post }) => {
  return (
    <Text fontSize="sm" color="gray.500">
      {formatDistance(new Date(post.createdTimeStamp), new Date(), {
        addSuffix: true,
      })}
    </Text>
  );
};

export default PostTimestamp;
