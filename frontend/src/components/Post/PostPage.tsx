import { useDeletePost } from "@/hooks/deletionMutations";
import usePost from "@/hooks/usePost";
import { useCurrentUser } from "@/hooks/useUser";
import { ArrowBackIcon } from "@chakra-ui/icons";
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
import { Link, useNavigate, useParams } from "react-router-dom";
import DeleteDialog from "../DeleteDialog";
import Error from "../Error";
import Loader from "../Loader";
import CommentBox from "./Comment/CommentBox";
import PostLike from "./PostLike";
import PostLikes from "./PostLikes";
import PostTimestamp from "./PostTimestamp";

const PostPage: React.FC = () => {
  const { postId: postIdParam } = useParams<{ postId: string }>();

  const postId = toNumber(postIdParam);

  const { user } = useCurrentUser();

  const deletePostMutation = useDeletePost(postId);

  const { post, isLoading, isError, error } = usePost(postId);

  const navigate = useNavigate();

  if (isError) {
    return <Error error={error} />;
  }

  if (isLoading || !post) {
    return <Loader />;
  }

  return (
    <Container>
      <Box borderWidth="1px" borderRadius="lg" p="4" boxShadow="md" w="full">
        <Flex alignItems={"center"}>
          <IconButton
            aria-label="Back"
            onClick={() => {
              navigate(-1);
            }}
          >
            <ArrowBackIcon />
          </IconButton>
          <Text p={4} fontSize="xl" fontWeight="bold">
            {post.title}
          </Text>
        </Flex>
        <Flex mt="2" align="center">
          <Avatar
            onClick={(e) => {
              e.preventDefault();
              e.stopPropagation();
              navigate(`/profile/${post.user.userId}`);
            }}
            cursor={"pointer"}
            name={post.user.username}
            src={post.user.profileUrl}
          />
          <Text ml="2" fontSize="md" fontWeight="semibold">
            {post.user.username}
          </Text>
        </Flex>
        <Text mt="2" color="gray.600">
          {post.text}
        </Text>
        <Flex mt="4" align="center" justify="space-between">
          <Badge
            as={Link}
            to={`/topics/${post.topic.topicId}`}
            colorScheme="teal"
          >
            {post.topic.topicName}
          </Badge>
          <PostTimestamp post={post} />
        </Flex>
        <PostLikes post={post} />
        <Flex mt="4" align="center" justify="space-between">
          <Flex align="center">
            <PostLike post={post} />
            {user?.userId == post.user.userId && (
              <DeleteDialog
                name={"Post"}
                doAction={() => {
                  deletePostMutation.mutate();
                }}
              />
            )}
          </Flex>

          <Flex align="center"></Flex>
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
