import { fetchAllTopics } from "@/api/topic";
import Error from "@/components/Error";
import Loader from "@/components/Loader";
import { useQuery } from "@tanstack/react-query";
import { FunctionComponent } from "react";
import TopicList from "./TopicList";

interface TopicPageProps {}

const TopicPage: FunctionComponent<TopicPageProps> = () => {
  const {
    data: topics,
    isLoading,
    isError,
  } = useQuery(["topics"], fetchAllTopics);

  if (isLoading) return <Loader />;

  if (isError || !topics) return <Error />;

  return <TopicList topics={topics} />;
};

export default TopicPage;
