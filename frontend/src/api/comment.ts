import Comment from "@/interfaces/comment";
import CommentLike from "@/interfaces/commentLike";
import { routedApi } from ".";

const api = routedApi("comments");

export function deleteComment(commentId: number) {
  return api<void>(`deleteComment?commentId=${commentId}`, {
    method: "DELETE",
  });
}

export function unlikeComment(commentId: number) {
  return api<void>(`unlikeComment?commentId=${commentId}`, {
    method: "DELETE",
  });
}

export function likeComment(commentId: number) {
  return api<CommentLike>(`likeComment?commentId=${commentId}`, {
    method: "POST",
  });
}

export function addComment(postId: number, text: string) {
  return api<Comment>(`addComment?postId=${postId}`, {
    method: "POST",
    data: { text },
    headers: {
      "Content-Type": "application/json",
    },
  });
}
