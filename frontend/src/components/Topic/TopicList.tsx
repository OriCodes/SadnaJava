import { Box, SimpleGrid } from "@chakra-ui/react";
import Topic from "../interfaces/topic";
import TopicCard from "./TopicCard";

interface TopicListProps {
  topics: Topic[];
}

const TopicList: React.FC<TopicListProps> = ({ topics }) => {
  return (
    <SimpleGrid columns={[1, 2, 3]} spacing={6} p={6}>
      {topics.map((topic) => (
        <Box key={topic.topicId} w="100%">
          <TopicCard topic={topic} />
        </Box>
      ))}
    </SimpleGrid>
  );
};

export default TopicList;
