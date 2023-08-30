import Post from "@/interfaces/post";
import PostLike from "@/interfaces/postLike";
import { routedApi } from ".";

const BASE_URL = "posts"; // Adjust the URL path as needed

const api = routedApi(BASE_URL);

export async function deletePost(postId: number): Promise<void> {
  await api<void>(`deletePost?postId=${postId}`, {
    method: "DELETE",
  });
}

export async function unlikePost(postId: number): Promise<void> {
  await api<void>(`unlikePost?postId=${postId}`, {
    method: "DELETE",
  });
}

export async function addPost(
  topicId: number,
  title: string,
  text: string
): Promise<Post> {
  const requestData = { topicId, title, text };
  return await api<Post>("addPost", {
    method: "POST",
    data: requestData,
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export async function likePost(postId: number): Promise<PostLike> {
  return await api<PostLike>(`likePost?postId=${postId}`, {
    method: "POST",
  });
}

export async function getAllPosts(): Promise<Post[]> {
  return await api<Post[]>("allPosts");
}

export async function getPostById(postId: number): Promise<Post> {
  return await api<Post>(`byId/${postId}`);
}

export async function hasLiked(
  postId: number,
  userId: number
): Promise<boolean> {
  return await api<boolean>("hasLiked", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    data: { postId, userId },
  });
}

export async function getAllPostsByTopic(topicId: number): Promise<Post[]> {
  return await api<Post[]>(`allByTopic?topicId=${topicId}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
}

export async function getAllPostsByUser(userId: number): Promise<Post[]> {
  return await api<Post[]>(`allByUser?userId=${userId}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
}
export async function searchPostByTitle(title: string): Promise<Post[]> {
  return await api<Post[]>(`searchPost?title=${title}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
}
export async function searchPostByTitleAndTopic(
  title: string,
  topicId: number
): Promise<Post[]> {
  return await api<Post[]>(`searchPost?title=${title}&topicId=${topicId}`, {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
  });
}
export async function existPostByTitle(title: string): Promise<boolean> {
  return await api<boolean>("existByTitle", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    data: { title },
  });
}

export async function existPostByTitleAndTopic(
  title: string,
  topicId: number
): Promise<boolean> {
  return await api<boolean>("existByTitle", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    data: { title, topicId },
  });
}
