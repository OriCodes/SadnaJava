import { useSendMessage } from "@/hooks/useMessages";
import { Box, Button, Textarea } from "@chakra-ui/react";
import React, { useState } from "react";

interface SendMessageFormProps {
  receiverId: number;
}

const SendMessageForm: React.FC<SendMessageFormProps> = ({ receiverId }) => {
  const [message, setMessage] = useState("");
  const { mutate, isLoading } = useSendMessage();

  const handleSendMessage = async () => {
    if (message.trim()) {
      await mutate({ message, receiverId });
      setMessage("");
    }
  };

  return (
    <Box p="4">
      <Textarea
        placeholder="Type your message..."
        value={message}
        onChange={(e) => setMessage(e.target.value)}
        size="sm"
        resize="vertical"
        mb="2"
      />
      <Button
        colorScheme="teal"
        size="sm"
        onClick={handleSendMessage}
        isLoading={isLoading}
      >
        Send
      </Button>
    </Box>
  );
};

export default SendMessageForm;
