import Topic from "@/interfaces/topic";
import { Box, Flex, Image, Text } from "@chakra-ui/react";
import React from "react";
import { Link } from "react-router-dom";

interface TopicCardProps {
  topic: Topic;
}

const TopicCard: React.FC<TopicCardProps> = ({ topic }) => {
  const { topicName, createdTimeStamp, thumbnailUrl, topicId } = topic;

  return (
    <Link to={`/topic/${topicId}`}>
      <Box
        borderWidth="1px"
        borderRadius="lg"
        overflow="hidden"
        boxShadow="md"
        p={4}
        bgColor="white"
        transition="transform 0.2s"
        _hover={{ transform: "scale(1.05)" }}
      >
        <Image
          src={thumbnailUrl}
          alt={topicName}
          height="40"
          width="full"
          objectFit="cover"
        />
        <Text mt={2} fontSize="lg" fontWeight="semibold">
          {topicName}
        </Text>
        <Flex mt={2} alignItems="center">
          <Text fontSize="sm" color="gray.500">
            Created on {new Date(createdTimeStamp).toDateString()}
          </Text>
        </Flex>
      </Box>
    </Link>
  );
};

export default TopicCard;
