import { getAllPostsByTopic } from "@/api/post";
import useTopic from "@/hooks/useTopic";
import { Box, Image, SimpleGrid, Text } from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { map, toNumber } from "lodash";
import { FunctionComponent } from "react";
import { useParams } from "react-router-dom";
import Error from "../Error";
import Loader from "../Loader";
import PostCard from "../Post/PostCard";

interface TopicPostsProps {}

const TopicPosts: FunctionComponent<TopicPostsProps> = () => {
  const { topicId } = useParams<{ topicId: string }>();

  const { topic } = useTopic(toNumber(topicId));

  const {
    data: posts,
    error,
    isLoading,
    isError,
  } = useQuery(["posts", topicId], () => getAllPostsByTopic(toNumber(topicId)));

  if (isError) {
    return <Error error={error} />;
  }

  if (isLoading) {
    return <Loader />;
  }

  return (
    <Box>
      <Box mb="4">
        <Image
          src={topic?.thumbnailUrl}
          alt={topic?.topicName}
          maxH="300px"
          width={"full"}
          objectFit="cover"
        />
        <Text fontSize="xl" fontWeight="bold">
          Posts in {topic?.topicName}
        </Text>
      </Box>
      {posts.length === 0 ? (
        <Text>No posts available for this topic.</Text>
      ) : (
        <SimpleGrid columns={[1, 2, 3, 4]} spacing={6} p={6}>
          {map(posts, (post) => (
            <PostCard key={post.postId} post={post} />
          ))}
        </SimpleGrid>
      )}
    </Box>
  );
};

export default TopicPosts;
