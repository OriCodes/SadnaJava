import { addComment } from "@/api/comment";
import Post from "@/interfaces/post";
import { Box, Button, Stack, Text, Textarea } from "@chakra-ui/react";
import { useMutation, useQueryClient } from "@tanstack/react-query";
import { FunctionComponent, useState } from "react";
import Comment from "./Comment";

interface CommentBoxProps {
  post: Post;
}

const CommentBox: FunctionComponent<CommentBoxProps> = ({ post }) => {
  const [commentText, setCommentText] = useState("");

  const queryClient = useQueryClient();

  const addCommentMutation = useMutation(
    ["addComment", post.postId],
    (commentText: string) => addComment(post.postId, commentText),
    {
      onSuccess: (newComment: Comment) => {
        queryClient.setQueryData(["post", post.postId], (oldData?: Post) =>
          oldData
            ? {
                ...oldData,
                comments: [newComment, ...oldData.comments],
              }
            : oldData
        );
      },
    }
  );

  const handleCommentSubmit = () => {
    addCommentMutation.mutate(commentText);
    setCommentText("");
  };

  return (
    <Box p="4" borderWidth="1px" borderRadius="md" boxShadow="md">
      <Textarea
        value={commentText}
        onChange={(e) => setCommentText(e.target.value)}
        placeholder="Write a comment..."
        mb="2"
      />
      <Button
        colorScheme="blue"
        onClick={handleCommentSubmit}
        disabled={
          addCommentMutation.isLoading || commentText.trim().length === 0
        }
      >
        Submit Comment
      </Button>
      {/* add loading icon if mutation is loading */}
      {addCommentMutation.isLoading && <Text>Adding comment...</Text>}

      <Stack mt="4" spacing="4">
        {post.comments.map((comment) => (
          <Comment
            key={comment.commentId}
            comment={comment}
            postId={post.postId}
          />
        ))}
      </Stack>
    </Box>
  );
};

export default CommentBox;
