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

export async function hasLiked(
  postId: number,
  userId: number
): Promise<boolean> {
  return await api<boolean>("hasLiked", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ postId, userId }),
  });
}

export async function getAllPostsByTopic(topicId: number): Promise<Post[]> {
  return await api<Post[]>("allByTopic", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ topicId }),
  });
}

export async function getAllPostsByUser(userId: number): Promise<Post[]> {
  return await api<Post[]>("allByUser", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ userId }),
  });
}

export async function searchPostByTitle(title: string): Promise<Post[]> {
  return await api<Post[]>("searchPost", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ title }),
  });
}

export async function searchPostByTitleAndTopic(
  title: string,
  topicId: number
): Promise<Post[]> {
  return await api<Post[]>("searchPost", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ title, topicId }),
  });
}

export async function existPostByTitle(title: string): Promise<boolean> {
  return await api<boolean>("existByTitle", {
    method: "GET",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ title }),
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
    body: JSON.stringify({ title, topicId }),
  });
}
