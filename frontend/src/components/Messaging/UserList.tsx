import { useGetUsersHavingConversations } from "@/hooks/useMessages";
import User from "@/interfaces/user";
import { Avatar, ListItem, UnorderedList } from "@chakra-ui/react";
import React from "react";

interface UserListProps {
  onSelectUser: (user: User) => void;
  selectedUser: User | null;
}

const UserList: React.FC<UserListProps> = ({ onSelectUser, selectedUser }) => {
  const { data: users } = useGetUsersHavingConversations();

  return (
    <UnorderedList listStyleType="none" p="4">
      {users?.map((user) => (
        <ListItem
          key={user.userId}
          p="2"
          cursor="pointer"
          bg={selectedUser?.userId === user.userId ? "gray.200" : "transparent"}
          onClick={() => onSelectUser(user)}
        >
          <Avatar size="xs" mr="2" src={user.profileUrl} />
          {user.username}
        </ListItem>
      ))}
    </UnorderedList>
  );
};

export default UserList;
