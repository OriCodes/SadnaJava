import Comment from "@/interfaces/comment";
import { Avatar, Box, Flex, Text } from "@chakra-ui/react";
import { formatDistanceToNow } from "date-fns"; // Import date-fns function
import { FunctionComponent } from "react";
import CommentLike from "./CommentLike";

interface CommentProps {
  comment: Comment;
}

const Comment: FunctionComponent<CommentProps> = ({ comment }) => {
  const formattedTimestamp = formatDistanceToNow(
    new Date(comment.createdTimeStamp),
    {
      addSuffix: true,
    }
  );

  return (
    <Box p="4" borderWidth="1px" borderRadius="md" boxShadow="md">
      <Flex alignItems="center">
        <Avatar src={comment.user.profileUrl} size="sm" mr="2" />
        <Text fontWeight="bold">{comment.user.username}</Text>
      </Flex>
      <Flex mt="2" align="center" width="full" justifyContent={"space-between"}>
        <Box>
          <Text mt="2">{comment.text}</Text>
          <Text mt="2" fontSize="sm" color="gray.500">
            {formattedTimestamp}
          </Text>
        </Box>
        <Flex mt="4" align="center" justify="space-between">
          <CommentLike comment={comment} />
        </Flex>
      </Flex>
    </Box>
  );
};

export default Comment;
