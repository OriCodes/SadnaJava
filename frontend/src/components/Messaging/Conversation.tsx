import { useConversation } from "@/hooks/useMessages";
import User from "@/interfaces/user";
import { Text, VStack } from "@chakra-ui/react";
import React from "react";
import { Link } from "react-router-dom";
import Message from "./Message";

interface ConversationProps {
  user: User;
}

const Conversation: React.FC<ConversationProps> = ({ user }) => {
  const { data: messages } = useConversation(user.userId);

  return (
    <VStack p="4" align="flex-start" spacing="2" w="full">
      <Text as={Link} to={`/profile/${user.userId}`} fontWeight="bold">
        {user.username}
      </Text>
      {messages?.map((message) => (
        <Message message={message} />
      ))}
      {messages?.length == 0 && <Text>No messages yet.</Text>}
    </VStack>
  );
};

export default Conversation;
