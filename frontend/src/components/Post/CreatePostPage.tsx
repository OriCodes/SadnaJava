import { addPost } from "@/api/post";
import { useTopics } from "@/hooks/useTopic";
import Post from "@/interfaces/post";
import {
  Box,
  Button,
  Container,
  FormControl,
  FormLabel,
  Input,
  Select,
  Textarea,
} from "@chakra-ui/react";
import { useMutation } from "@tanstack/react-query";
import { toNumber } from "lodash";
import React, { FunctionComponent, useState } from "react";
import { useNavigate } from "react-router-dom";
import Error from "../Error";
import Loader from "../Loader";

interface CreatePostPageProps {}

const CreatePostPage: FunctionComponent<CreatePostPageProps> = () => {
  const [title, setTitle] = useState("");
  const [content, setContent] = useState("");

  const { topics, isLoading, error, isError } = useTopics();

  const [selectedTopic, setSelectedTopic] = useState(1);
  const navigate = useNavigate();

  const createPostMutation = useMutation(
    ["createPost", { title, content, selectedTopic }],
    () => addPost(selectedTopic, title, content),
    {
      onSuccess: (newPost: Post) => {
        navigate(`/posts/${newPost.postId}`);
      },
    }
  );

  if (isLoading || !topics) return <Loader />;

  if (isError) return <Error error={error} />;

  const handleTitleChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setTitle(event.target.value);
  };

  const handleContentChange = (
    event: React.ChangeEvent<HTMLTextAreaElement>
  ) => {
    setContent(event.target.value);
  };

  const handleTopicChange = (event: React.ChangeEvent<HTMLSelectElement>) => {
    setSelectedTopic(toNumber(event.target.value));
  };

  const handleSubmit = (event: React.FormEvent) => {
    event.preventDefault();

    createPostMutation.mutate();
  };

  return (
    <Container maxW="container.md" mt="4">
      <Box p="4" borderWidth="1px" borderRadius="md" boxShadow="md">
        <form onSubmit={handleSubmit}>
          <FormControl id="title" mb="4">
            <FormLabel>Title</FormLabel>
            <Input
              type="text"
              value={title}
              onChange={handleTitleChange}
              required
            />
          </FormControl>

          <FormControl id="content" mb="4">
            <FormLabel>Content</FormLabel>
            <Textarea value={content} onChange={handleContentChange} required />
          </FormControl>

          <FormControl id="topic" mb="4">
            <FormLabel>Topic</FormLabel>
            <Select value={selectedTopic} onChange={handleTopicChange} required>
              {topics.map((topic) => (
                <option key={topic.topicId} value={topic.topicId}>
                  {topic.topicName}
                </option>
              ))}
            </Select>
          </FormControl>

          <Button type="submit">Create Post</Button>
        </form>
      </Box>
    </Container>
  );
};

export default CreatePostPage;
