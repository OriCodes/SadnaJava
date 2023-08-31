import { useCurrentUser } from "@/hooks/useUser";
import { Message } from "@/interfaces/message";
import { Avatar, Box, Text } from "@chakra-ui/react";
import { formatDistanceToNow } from "date-fns";
import { FunctionComponent } from "react";

interface MessageProps {
  message: Message;
}

const Message: FunctionComponent<MessageProps> = ({ message }) => {
  const { user } = useCurrentUser();

  const isUserSender = user?.userId == message.sender.userId;

  return (
    <Box
      key={message.messageId}
      p="2"
      bg={isUserSender ? "teal.100" : "gray.200"}
      borderRadius="md"
      alignSelf={isUserSender ? "flex-end" : "flex-start"}
    >
      <Avatar size="xs" mr="2" src={message.sender.profileUrl} />
      {message.content}
      <Text fontSize="xs" textAlign="right">
        {formatDistanceToNow(new Date(message.createdTimeStamp), {
          addSuffix: true,
        })}
      </Text>
    </Box>
  );
};

export default Message;
