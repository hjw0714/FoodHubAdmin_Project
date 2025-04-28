package com.application.foodhubAdmin.service;

import com.application.foodhubAdmin.domain.Post;
import com.application.foodhubAdmin.domain.PostStatus;
import com.application.foodhubAdmin.repository.AdminPostListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminPostListService {

    private final AdminPostListRepository adminPostListRepository;

    public List<Post> findNoticePosts() {
        return adminPostListRepository.findByCategoryNm("공지사항");
    }

    @Transactional
    public void deletePost(Long postId) throws IllegalAccessException {
        Post post = adminPostListRepository.findById(postId).orElseThrow(()-> new IllegalAccessException("해당 게시글을 찾을 수 없습니다."));
        post.changeStatus(PostStatus.DELETED);
    }

    @Transactional
    public void restorePost(Long postId) throws IllegalAccessException {
        Post post = adminPostListRepository.findById(postId).orElseThrow(() -> new IllegalAccessException("해당 게시글을 찾을 수 없습니다."));
        post.changeStatus(PostStatus.ACTIVE);
    }
}
