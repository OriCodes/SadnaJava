import { Message } from "@/interfaces/message";
import User from "@/interfaces/user";
import { routedApi } from ".";

const api = routedApi("messages");

export const sendMessage = (message: string, receiverId: number) =>
  api<Message>(`sendMessage?receiverId=${receiverId}`, {
    data: { message },
    method: "POST",
  });

export const getConversation = (userId: number) =>
  api<Message[]>(`getConversation?userId=${userId}`);

export const hasConversationBetween = (userId: number) =>
  api<boolean>(`hasConversationBetween?userId=${userId}`);

export const byId = (messageId: number) => api<Message>(`byId/${messageId}`);

export const messageSentCounter = (userId: number) =>
  api<number>(`messageSentCounter?userId=${userId}`);

export const newMessages = (timestamp: string) =>
  api<Message[]>(`newMessages?timestamp=${timestamp}`);

export const getUsersHavingConversations = () =>
  api<User[]>("usersHavingConversations");
