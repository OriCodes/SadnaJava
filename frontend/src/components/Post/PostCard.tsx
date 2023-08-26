import Post from "@/interfaces/post";
import {
  Avatar,
  Badge,
  Box,
  Flex,
  IconButton,
  Text,
  Tooltip,
} from "@chakra-ui/react";
import React from "react";
import { AiOutlineComment } from "react-icons/ai";
import { Link } from "react-router-dom";
import PostLike from "./PostLike";
import PostLikes from "./PostLikes";
import PostTimestamp from "./PostTimestamp";

interface PostCardProps {
  post: Post;
}
const PostCard: React.FC<PostCardProps> = ({ post }) => {
  const shortenedText =
    post.text.length > 100 ? post.text.substring(0, 100) + "..." : post.text;

  return (
    <Link to={`/posts/${post.postId}`}>
      <Box
        borderWidth="1px"
        borderRadius="lg"
        p="4"
        boxShadow="md"
        maxW="md"
        w="full"
        cursor="pointer"
      >
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
          {shortenedText}
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
      </Box>
    </Link>
  );
};

export default PostCard;
