import { api } from ".";
import Topic from "../interfaces/topic";

function topicApi<T>(endpoint: string): Promise<T> {
  return api<T>(`topic/${endpoint}`);
}

const fetchAllTopics = (): Promise<Topic[]> => {
  return topicApi<Topic[]>(`allTopics`);
};

const fetchTopicsBySeq = (seq: string): Promise<Topic[]> => {
  return topicApi<Topic[]>(`allTopics?seq=${seq}`);
};

const fetchTopicById = (topicId: number): Promise<Topic> => {
  return topicApi<Topic>(`byId/${topicId}`);
};

const fetchTopicByTitle = (topicName: string): Promise<Topic> => {
  return topicApi<Topic>(`${topicName}`);
};

const checkTopicExistByTitle = (title: string): Promise<boolean> => {
  return topicApi<boolean>(`existByTitle?title=${title}`);
};

export {
  checkTopicExistByTitle,
  fetchAllTopics,
  fetchTopicById,
  fetchTopicByTitle,
  fetchTopicsBySeq,
};
