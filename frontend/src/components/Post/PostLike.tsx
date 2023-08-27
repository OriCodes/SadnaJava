import { likePost, unlikePost } from "@/api/post";
import { useCurrentUser } from "@/hooks/useUser";
import Post from "@/interfaces/post";
import PostLike from "@/interfaces/postLike";
import { IconButton, Text, Tooltip } from "@chakra-ui/react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { FunctionComponent } from "react";
import { AiFillHeart, AiOutlineHeart } from "react-icons/ai";

interface PostLikeProps {
  post: Post;
}

const PostLike: FunctionComponent<PostLikeProps> = ({ post }) => {
  const { user, loggedIn } = useCurrentUser();

  const hasLiked =
    loggedIn && post.likes.some((like) => like.user.userId === user?.userId);

  const queryClient = useQueryClient();

  const likeMutation = useMutation(
    ["like", post.postId],
    () => {
      return likePost(post.postId);
    },
    {
      onSuccess: (newLike: PostLike) => {
        queryClient.setQueryData(["post", post.postId], (oldData?: Post) =>
          oldData
            ? {
                ...oldData,
                likes: [...oldData.likes, newLike],
              }
            : oldData
        );

        queryClient.setQueryData(
          ["posts", post.topic.topicId],
          (posts?: Post[]) =>
            posts?.map((oldPost) =>
              oldPost.postId === post.postId
                ? {
                    ...oldPost,
                    likes: [...oldPost.likes, newLike],
                  }
                : oldPost
            )
        );
      },
    }
  );

  const unlikeMutation = useMutation(
    ["unlike", post.postId],
    () => unlikePost(post.postId),
    {
      onSuccess: () => {
        queryClient.setQueryData(["post", post.postId], (oldData?: Post) =>
          oldData
            ? {
                ...oldData,
                likes: oldData.likes.filter(
                  (like) => like.user.userId !== user?.userId
                ),
              }
            : oldData
        );

        queryClient.setQueryData(
          ["posts", post.topic.topicId],
          (posts?: Post[]) =>
            posts?.map((oldPost) =>
              oldPost.postId === post.postId
                ? {
                    ...oldPost,
                    likes: oldPost.likes.filter(
                      (like) => like.user.userId !== user?.userId
                    ),
                  }
                : oldPost
            )
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
            if (loggedIn) {
              if (hasLiked) {
                unlikeMutation.mutate();
              } else {
                likeMutation.mutate();
              }
            }
          }}
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
