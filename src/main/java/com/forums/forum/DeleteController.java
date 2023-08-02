package com.forums.forum;

import com.forums.forum.service.DeleteService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "api/delete")
@AllArgsConstructor
public class DeleteController {

    private final DeleteService deleteService;

    @DeleteMapping(path = "/unfollow")
    public void unfollow(@RequestParam Long followerId, @RequestParam Long followedId)
    {
            try{
                deleteService.unfollow(followerId, followedId);
            }catch (IllegalArgumentException e)
            {
                e.printStackTrace();
            }
    }

    @DeleteMapping(path = "/unlikePost")
    public void unlikePost(@RequestParam Long postId, @RequestParam Long userId)
    {
        try{
            deleteService.unlikePost(postId, userId);
        }catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @DeleteMapping(path = "/unlikeComment")
    public void unlikeComment(@RequestParam Long commentId, @RequestParam Long userId)
    {
        try{
            deleteService.unlikeComment(commentId, userId);
        }catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @DeleteMapping(path = "/deleteComment")
    public void deleteComment(@RequestParam Long commentId, @RequestParam Long userId)
    {
        try{
            deleteService.deleteComment(commentId, userId);
        }catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @DeleteMapping(path = "/deletePost")
    public void deletePost(@RequestParam Long postId, @RequestParam Long userId)
    {
        try{
            deleteService.deletePost(postId, userId);
        }catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }
    @DeleteMapping(path = "/deleteUser")
    public void deleteUser(@RequestParam Long userId)
    {
        try{
            deleteService.deleteUser(userId);
        }catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }

    @DeleteMapping(path = "/deleteTopic")
    public void deleteTopic(@RequestParam Long topicId)
    {
        try{
            deleteService.deleteTopic(topicId);
        }catch (IllegalArgumentException e)
        {
            e.printStackTrace();
        }
    }


}
