import Post from "@/interfaces/post";
import { SimpleGrid, Text } from "@chakra-ui/react";
import { map } from "lodash";
import { FunctionComponent } from "react";
import PostCard from "./PostCard";

interface PostListProps {
  posts: Post[];
}

const PostList: FunctionComponent<PostListProps> = ({ posts }) => {
  return posts.length === 0 ? (
    <Text>No posts available</Text>
  ) : (
    <SimpleGrid columns={[1, 2]} spacing={6} p={6}>
      {map(posts, (post) => (
        <PostCard key={post.postId} post={post} />
      ))}
    </SimpleGrid>
  );
};

export default PostList;
