import { fetchAllTopics, fetchTopicById } from "@/api/topic";
import { useQuery } from "@tanstack/react-query";

const useTopic = (topicId: number) => {
  const query = useQuery(["topic", topicId], () => fetchTopicById(topicId));

  return {
    ...query,
    topic: query.data,
  };
};

export default useTopic;

export const useTopics = () => {
  const query = useQuery(["topics"], fetchAllTopics);

  return {
    ...query,
    topics: query.data,
  };
};
