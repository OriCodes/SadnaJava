import Post from "@/interfaces/post"; // Adjust the import path as needed
import { Box, Button, Stack, Textarea } from "@chakra-ui/react";
import { FunctionComponent, useState } from "react";
import Comment from "./Comment";

interface CommentBoxProps {
  post: Post;
}

const CommentBox: FunctionComponent<CommentBoxProps> = ({ post }) => {
  const [commentText, setCommentText] = useState("");

  const handleCommentSubmit = () => {
    console.log("Comment submitted:", commentText);
    // You can add logic to send the comment to your backend here
    setCommentText(""); // Clear the input after submission
  };

  return (
    <Box p="4" borderWidth="1px" borderRadius="md" boxShadow="md">
      <Textarea
        value={commentText}
        onChange={(e) => setCommentText(e.target.value)}
        placeholder="Write a comment..."
        mb="2"
      />
      <Button colorScheme="blue" onClick={handleCommentSubmit}>
        Submit Comment
      </Button>
      <Stack mt="4" spacing="4">
        {post.comments.map((comment) => (
          <Comment key={comment.commentId} comment={comment} />
        ))}
      </Stack>
    </Box>
  );
};

export default CommentBox;
