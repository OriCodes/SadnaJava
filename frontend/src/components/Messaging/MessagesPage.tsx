import User from "@/interfaces/user";
import { Box, Container, Divider, Flex, VStack } from "@chakra-ui/react";
import React, { useState } from "react";
import Conversation from "./Conversation"; // Assume you've created a Conversation component
import SendMessageForm from "./SendMessageForm"; // Assume you've created a SendMessageForm component
import UserList from "./UserList"; // Assume you've created a UserList component

const MessagesPage: React.FC = () => {
  const [selectedUser, setSelectedUser] = useState<User | null>(null);

  const handleUserSelect = (user: User) => {
    setSelectedUser(user);
  };

  return (
    <Container maxW="100%" mt="4">
      <Flex>
        <Box w="300px" mr="4">
          <UserList
            onSelectUser={handleUserSelect}
            selectedUser={selectedUser}
          />
        </Box>
        <Divider orientation="vertical" />
        <Flex
          w="50%"
          direction="column"
          justify="center"
          align="center"
          h={"full"}
        >
          <Box flex="1">
            {selectedUser ? (
              <Conversation user={selectedUser} />
            ) : (
              <VStack align="center" justify="center" h="full">
                <Box>No user selected.</Box>
              </VStack>
            )}
          </Box>
          <Divider orientation="vertical" h={"full"} />
          <Box w="300px" ml="4">
            {selectedUser && (
              <SendMessageForm receiverId={selectedUser.userId} />
            )}
          </Box>
        </Flex>
      </Flex>
    </Container>
  );
};

export default MessagesPage;
