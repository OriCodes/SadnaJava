import Topic from "@/interfaces/topic";
import { routedApi } from ".";

const api = routedApi("topics");

const fetchAllTopics = (): Promise<Topic[]> => {
  return api<Topic[]>(`allTopics`);
};

const fetchTopicsBySeq = (seq: string): Promise<Topic[]> => {
  return api<Topic[]>(`allTopics?seq=${seq}`);
};

const fetchTopicById = (topicId: number): Promise<Topic> => {
  return api<Topic>(`byId/${topicId}`);
};

const fetchTopicByTitle = (topicName: string): Promise<Topic> => {
  return api<Topic>(`${topicName}`);
};

const checkTopicExistByTitle = (title: string): Promise<boolean> => {
  return api<boolean>(`existByTitle?title=${title}`);
};

export {
  checkTopicExistByTitle,
  fetchAllTopics,
  fetchTopicById,
  fetchTopicByTitle,
  fetchTopicsBySeq,
};
