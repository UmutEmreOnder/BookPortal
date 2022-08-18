package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.CommentDto;
import tr.com.obss.jip.dto.create.CreateNewComment;

import java.util.List;

public interface CommentService {
    void addComment(Long bookId, CreateNewComment comment);

    boolean deleteComment(Long id);
    List<CommentDto> getComments(Long bookId, Integer page, Integer pageSize);
}
