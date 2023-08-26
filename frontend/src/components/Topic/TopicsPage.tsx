import { fetchAllTopics } from "@/api/topic";
import Error from "@/components/Error";
import Loader from "@/components/Loader";
import { useQuery } from "@tanstack/react-query";
import { FunctionComponent } from "react";
import TopicList from "./TopicList";

interface TopicPageProps {}

const TopicsPage: FunctionComponent<TopicPageProps> = () => {
  const {
    data: topics,
    isLoading,
    isError,
    error,
  } = useQuery(["topics"], fetchAllTopics);

  if (isLoading) return <Loader />;

  if (isError || !topics) return <Error error={error} />;

  return <TopicList topics={topics} />;
};

export default TopicsPage;
