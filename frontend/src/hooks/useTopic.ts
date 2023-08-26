import { fetchTopicById } from "@/api/topic";
import { useQuery } from "@tanstack/react-query";

const useTopic = (topicId: number) => {
  const query = useQuery(["topic", topicId], () => fetchTopicById(topicId));

  return {
    ...query,
    topic: query.data,
  };
};

export default useTopic;
