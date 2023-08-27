import { getAllPostsByTopic } from "@/api/post";
import useTopic from "@/hooks/useTopic";
import { ArrowBackIcon } from "@chakra-ui/icons";
import {
  Box,
  Flex,
  IconButton,
  Image,
  SimpleGrid,
  Text,
} from "@chakra-ui/react";
import { useQuery } from "@tanstack/react-query";
import { map, toNumber } from "lodash";
import { FunctionComponent } from "react";
import { useNavigate, useParams } from "react-router-dom";
import Error from "../Error";
import Loader from "../Loader";
import PostCard from "../Post/PostCard";

interface TopicPostsProps {}

const TopicPosts: FunctionComponent<TopicPostsProps> = () => {
  const { topicId: paramTopicId } = useParams<{ topicId: string }>();

  const topicId = toNumber(paramTopicId);

  const { topic } = useTopic(topicId);

  const {
    data: posts,
    error,
    isLoading,
    isError,
  } = useQuery(["posts", topicId], () => getAllPostsByTopic(topicId));

  const navigate = useNavigate();

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
        <Flex alignItems="center">
          <IconButton
            aria-label="back"
            onClick={() => {
              navigate(-1);
            }}
          >
            <ArrowBackIcon />
          </IconButton>
          <Text p={4} fontSize="xl" fontWeight="bold">
            Posts in {topic?.topicName}
          </Text>
        </Flex>
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
