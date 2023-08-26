import { likeComment, unlikeComment } from "@/api/comment";
import useUser from "@/hooks/useUser";
import Comment from "@/interfaces/comment";
import CommentLike from "@/interfaces/commentLike";
import Post from "@/interfaces/post";
import { IconButton, Text, Tooltip } from "@chakra-ui/react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { FunctionComponent } from "react";
import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";

interface CommentLikeProps {
  comment: Comment;
  postId: number;
}

const CommentLike: FunctionComponent<CommentLikeProps> = ({
  comment,
  postId,
}) => {
  const { user, loggedIn } = useUser();

  const hasLiked =
    loggedIn && comment.likes.some((like) => like.user.userId === user?.userId);

  const queryClient = useQueryClient();

  const likeMutation = useMutation(
    ["like", comment.commentId],
    () => likeComment(comment.commentId),
    {
      onSuccess: (newLike: CommentLike) => {
        queryClient.setQueryData(["post", postId], (oldData?: Post) =>
          oldData
            ? {
                ...oldData,
                comments: oldData.comments.map((oldComment) =>
                  oldComment.commentId === comment.commentId
                    ? {
                        ...oldComment,
                        likes: [...oldComment.likes, newLike],
                      }
                    : oldComment
                ),
              }
            : oldData
        );
      },
    }
  );

  const unlikeMutation = useMutation(
    ["unlike", comment.commentId],
    () => unlikeComment(comment.commentId),
    {
      onSuccess: () => {
        queryClient.setQueryData(["post", postId], (oldData?: Post) =>
          oldData
            ? {
                ...oldData,
                comments: oldData.comments.map((oldComment) =>
                  oldComment.commentId === comment.commentId
                    ? {
                        ...oldComment,
                        likes: oldComment.likes.filter(
                          (like) => like.user.userId !== user?.userId
                        ),
                      }
                    : oldComment
                ),
              }
            : oldData
        );
      },
    }
  );

  return (
    <>
      <Tooltip label="Like" placement="top">
        <IconButton
          onClick={(e) => {
            e.preventDefault();
            if (hasLiked) {
              unlikeMutation.mutate();
            } else {
              likeMutation.mutate();
            }
          }}
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
