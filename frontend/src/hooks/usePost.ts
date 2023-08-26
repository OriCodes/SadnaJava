import { getPostById } from "@/api/post";
import { useQuery } from "@tanstack/react-query";

const usePost = (postId: number) => {
  const query = useQuery(["post", postId], () => getPostById(postId));

  return {
    ...query,
    post: query.data,
  };
};

export default usePost;
