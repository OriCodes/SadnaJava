import usePost from "@/hooks/usePost";
import {
  Avatar,
  Badge,
  Box,
  Container,
  Flex,
  IconButton,
  Text,
  Tooltip,
} from "@chakra-ui/react";
import { toNumber } from "lodash";
import React from "react";
import { AiOutlineComment } from "react-icons/ai";
import { useParams } from "react-router-dom";
import Error from "../Error";
import Loader from "../Loader";
import CommentBox from "./Comment/CommentBox";
import PostLike from "./PostLike";
import PostLikes from "./PostLikes";
import PostTimestamp from "./PostTimestamp";

const PostPage: React.FC = () => {
  const { postId } = useParams<{ postId: string }>();

  const { post, isLoading, isError, error } = usePost(toNumber(postId));

  if (isError) {
    return <Error error={error} />;
  }

  if (isLoading || !post) {
    return <Loader />;
  }

  return (
    <Container>
      <Box borderWidth="1px" borderRadius="lg" p="4" boxShadow="md" w="full">
        <Text fontSize="xl" fontWeight="bold">
          {post.title}
        </Text>
        <Flex mt="2" align="center">
          <Avatar name={post.user.username} src={post.user.profileUrl} />
          <Text ml="2" fontSize="md" fontWeight="semibold">
            {post.user.username}
          </Text>
        </Flex>
        <Text mt="2" color="gray.600">
          {post.text}
        </Text>
        <Flex mt="4" align="center" justify="space-between">
          <Badge colorScheme="teal">{post.topic.topicName}</Badge>
          <PostTimestamp post={post} />
        </Flex>
        <PostLikes post={post} />
        <Flex mt="4" align="center" justify="space-between">
          <Flex align="center">
            <PostLike post={post} />
          </Flex>
          <Flex align="center">
            <Tooltip label="Comment" placement="top">
              <IconButton
                icon={<AiOutlineComment />}
                aria-label="Comment"
                variant="ghost"
              />
            </Tooltip>
            <Text ml="1" fontSize="sm">
              {post.comments.length}
            </Text>
          </Flex>
        </Flex>
        <CommentBox post={post} />
      </Box>
    </Container>
  );
};

export default PostPage;
