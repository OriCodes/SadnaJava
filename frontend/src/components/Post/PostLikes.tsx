import Post from "@/interfaces/post";
import { Avatar, AvatarGroup, Flex, Text } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { useNavigate } from "react-router-dom";

interface PostLikesProps {
  post: Post;
}

const PostLikes: FunctionComponent<PostLikesProps> = ({ post }) => {
  const navigate = useNavigate();

  return (
    <Flex mt="4" align="center">
      <AvatarGroup size="sm" max={3}>
        {post.likes.map((like) => (
          <Avatar
            onClick={(e) => {
              e.preventDefault();
              e.stopPropagation();
              navigate(`/profile/${like.user.userId}`);
            }}
            cursor={"pointer"}
            key={like.postLikeId}
            name={like.user.username}
            src={like.user.profileUrl}
          />
        ))}
      </AvatarGroup>
      <Text ml="2" fontSize="sm" color="gray.500">
        Liked by {post.likes.length} people
      </Text>
    </Flex>
  );
};

export default PostLikes;
