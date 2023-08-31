import { useFollowing } from "@/hooks/useFollowing";
import { useGetUsersHavingConversations } from "@/hooks/useMessages";
import { useCurrentUser } from "@/hooks/useUser";
import User from "@/interfaces/user";
import { Avatar, ListItem, Text, UnorderedList } from "@chakra-ui/react";
import { filter, map } from "lodash";
import React from "react";

interface UserListProps {
  onSelectUser: (user: User) => void;
  selectedUser: User | null;
}

const UserList: React.FC<UserListProps> = ({ onSelectUser, selectedUser }) => {
  const { user } = useCurrentUser();

  const { data: users } = useGetUsersHavingConversations();

  const { following } = useFollowing(user?.userId as number);

  const followingUsersNotHavingConversations = filter(
    map(following, (user) => user.followed),
    (user) => !users?.some((u) => u.userId === user.userId)
  );

  return (
    <>
      <UnorderedList listStyleType="none" p="4">
        <Text fontSize="lg" fontWeight="bold">
          Chats
        </Text>
        {users?.map((user) => (
          <ListItem
            key={user.userId}
            p="2"
            cursor="pointer"
            bg={
              selectedUser?.userId === user.userId ? "gray.200" : "transparent"
            }
            onClick={() => onSelectUser(user)}
          >
            <Avatar size="xs" mr="2" src={user.profileUrl} />
            {user.username}
          </ListItem>
        ))}
      </UnorderedList>
      <UnorderedList listStyleType="none" p="4">
        <Text fontSize="lg" fontWeight="bold">
          Following
        </Text>
        {followingUsersNotHavingConversations?.map((user) => (
          <ListItem
            key={user.userId}
            p="2"
            cursor="pointer"
            bg={
              selectedUser?.userId === user.userId ? "gray.200" : "transparent"
            }
            onClick={() => onSelectUser(user)}
          >
            <Avatar size="xs" mr="2" src={user.profileUrl} />
            {user.username}
          </ListItem>
        ))}
        {followingUsersNotHavingConversations?.length === 0 && (
          <Text fontSize="sm" color="gray.500">
            You are not following anyone
          </Text>
        )}
      </UnorderedList>
    </>
  );
};

export default UserList;
