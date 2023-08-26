import useUser from "@/hooks/useUser";
import Comment from "@/interfaces/comment";
import { IconButton, Text, Tooltip } from "@chakra-ui/react";
import { FunctionComponent } from "react";
import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";

interface CommentLikeProps {
  comment: Comment;
}

const CommentLike: FunctionComponent<CommentLikeProps> = ({ comment }) => {
  const { user, loggedIn } = useUser();

  const hasLiked =
    loggedIn && comment.likes.some((like) => like.user.userId === user?.userId);

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
        {comment.likes.length}
      </Text>
    </>
  );
};

export default CommentLike;
