import {
  getConversation as getApiConversation,
  byId as getApiMessageById,
  messageSentCounter as getApiMessageSentCounter,
  newMessages as getApiNewMessages,
  getUsersHavingConversations as getApiUsersHavingConversations,
  hasConversationBetween as hasApiConversationBetween,
  sendMessage as sendApiMessage,
} from "@/api/message"; // Adjust the import paths as needed
import { Message } from "@/interfaces/message";
import User from "@/interfaces/user";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";

export function useSendMessage(message: string, receiverId: number) {
  const queryClient = useQueryClient();
  return useMutation(
    ["sendMessage", { message, receiverId }],
    () => sendApiMessage(message, receiverId),
    {
      onSuccess: (newMessage: Message) => {
        queryClient.invalidateQueries([
          "getConversation",
          newMessage.receiver.userId,
        ]);
      },
    }
  );
}

export function useConversation(userId: number) {
  return useQuery<Message[]>(["getConversation", userId], () =>
    getApiConversation(userId)
  );
}

export function useHasConversationBetween(userId: number) {
  return useQuery<boolean>(["hasConversationBetween", userId], () =>
    hasApiConversationBetween(userId)
  );
}

export function useGetMessageById(messageId: number) {
  return useQuery<Message>(["getMessageById", messageId], () =>
    getApiMessageById(messageId)
  );
}

export function useGetMessageSentCounter(userId: number) {
  return useQuery<number>(["getMessageSentCounter", userId], () =>
    getApiMessageSentCounter(userId)
  );
}

export function useGetNewMessages(timestamp: string) {
  return useQuery<Message[]>(["getNewMessages", timestamp], () =>
    getApiNewMessages(timestamp)
  );
}

export function useGetUsersHavingConversations() {
  return useQuery<User[]>(["getUsersHavingConversations"], () =>
    getApiUsersHavingConversations()
  );
}
