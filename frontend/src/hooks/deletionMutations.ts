import { deleteComment } from "@/api/comment";
import { deletePost } from "@/api/post";
import Post from "@/interfaces/post";
import { useToast } from "@chakra-ui/react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

export const useDeleteComment = (commentId: number, postId: number) => {
  const queryClient = useQueryClient();
  const toast = useToast();

  return useMutation(["Delete comment"], () => deleteComment(commentId), {
    onSuccess: () => {
      queryClient.invalidateQueries(["post", postId]);

      toast({
        title: "Post deleted",
        status: "success",
        duration: 3000,
        isClosable: true,
      });
    },
  });
};

export const useDeletePost = (postId: number) => {
  const queryClient = useQueryClient();

  const toast = useToast();
  const navigate = useNavigate();

  return useMutation(["Delete post"], () => deletePost(postId), {
    onSuccess: () => {
      queryClient.setQueryData(["post", postId], (post?: Post) =>
        post
          ? {
              ...post,
              text: "[Deleted Post]",
              title: "[Deleted Post]",
            }
          : post
      );

      navigate("/");

      toast({
        title: "Post deleted",
        status: "success",
        duration: 3000,
        isClosable: true,
      });
    },
  });
};
