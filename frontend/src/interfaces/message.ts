import User from "./user";

export interface Message {
  messageId: number;
  content: string;
  sender: User;
  receiver: User;
  createdTimeStamp: number;
}
