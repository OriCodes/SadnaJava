import PostLike from "@/interfaces/like";
import Post from "@/interfaces/post";
import { routedApi } from ".";

const BASE_URL = "posts"; // Adjust the URL path as needed

const api = routedApi(BASE_URL);

export async function deletePost(
  postId: number,
  userId: number
): Promise<void> {
  await api<void>("deletePost", {
    method: "DELETE",
    body: JSON.stringify({ postId, userId }),
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export async function unlikePost(
  postId: number,
  userId: number
): Promise<void> {
  await api<void>("unlikePost", {
    method: "DELETE",
    body: JSON.stringify({ postId, userId }),
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export async function addPost(
  userId: number,
  topicId: number,
  title: string,
  text: string
): Promise<Post> {
  const requestData = { userId, topicId, title, text };
  return await api<Post>("addPost", {
    method: "POST",
    body: JSON.stringify(requestData),
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export async function likePost(
  userId: number,
  postId: number
): Promise<PostLike> {
  return await api<PostLike>("likePost", {
    method: "POST",
    body: JSON.stringify({ userId, postId }),
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export async function getAllPosts(): Promise<Post[]> {
  return await api<Post[]>("allPosts");
}

export async function getPostById(postId: number): Promise<Post> {
  return await api<Post>(`byId/${postId}`);
}

// Add more functions for other endpoints
