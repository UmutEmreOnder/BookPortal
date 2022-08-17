package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.create.CreateNewComment;

public interface CommentService {
    void addComment(Long bookId, CreateNewComment comment);

    boolean deleteComment(Long id);
}
