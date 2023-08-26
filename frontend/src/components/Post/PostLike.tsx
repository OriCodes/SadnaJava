import useUser from "@/hooks/useUser";
import Post from "@/interfaces/post";
import { IconButton, Text, Tooltip } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";

interface PostLikeProps {
  post: Post;
}

const PostLike: FunctionComponent<PostLikeProps> = ({ post }) => {
  const { user, loggedIn } = useUser();

  const hasLiked =
    loggedIn && post.likes.some((like) => like.user.userId === user?.userId);

  return (
    <>
      <Tooltip label="Like" placement="top">
        <IconButton
          color={hasLiked ? "red.500" : "gray.500"}
          icon={hasLiked ? <AiFillHeart /> : <AiOutlineHeart />}
          aria-label="Like"
          variant="ghost"
        />
      </Tooltip>

      <Text ml="1" fontSize="sm">
        {post.likes.length}
      </Text>
    </>
  );
};

export default PostLike;
