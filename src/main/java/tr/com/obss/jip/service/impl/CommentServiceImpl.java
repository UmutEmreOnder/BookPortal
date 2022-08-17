package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.create.CreateNewComment;
import tr.com.obss.jip.exception.BookNotFoundException;
import tr.com.obss.jip.exception.CommentNotFoundException;
import tr.com.obss.jip.exception.UserNotFoundException;
import tr.com.obss.jip.model.Book;
import tr.com.obss.jip.model.Comment;
import tr.com.obss.jip.model.User;
import tr.com.obss.jip.repository.BookRepository;
import tr.com.obss.jip.repository.CommentRepository;
import tr.com.obss.jip.repository.UserRepository;
import tr.com.obss.jip.service.CommentService;

@Service
@Slf4j
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final BookRepository bookRepository;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;

    @Override
    public void addComment(Long bookId, CreateNewComment comment) {
        Book book = bookRepository.findById(bookId).orElseThrow(() -> new BookNotFoundException(bookId));
        User user = getAuthenticatedUser();

        Comment commentModel = Comment
                .builder()
                .comment(comment.getComment())
                .book(book)
                .user(user)
                .build();

        book.getComments().add(commentModel);
        user.getComments().add(commentModel);

        commentRepository.save(commentModel);
    }

    @Override
    public boolean deleteComment(Long id) {
        Comment comment = commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException(id));

        if (comment.getUser() == getAuthenticatedUser()) {
            comment.getUser().getComments().remove(comment);
            comment.getBook().getComments().remove(comment);
            commentRepository.deleteById(id);
            return true;
        }

        return false;
    }

    private User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return userRepository.findUserByUsername(currentUserName).orElseThrow(UserNotFoundException::new);
        }

        return null;
    }
}
