package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.create.CreateNewBook;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.exception.RequestNotFoundException;
import tr.com.obss.jip.mapper.BookMapper;
import tr.com.obss.jip.mapper.RequestMapper;
import tr.com.obss.jip.model.AddingBookRequest;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.RespondType;
import tr.com.obss.jip.repository.AuthorRepository;
import tr.com.obss.jip.repository.GenreRepository;
import tr.com.obss.jip.repository.RequestRepository;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.RequestService;
import tr.com.obss.jip.service.RespondedRequestService;
import tr.com.obss.jip.util.Helper;

import javax.persistence.EntityManager;
import java.util.Date;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final AuthorRepository authorRepository;
    private final RequestMapper requestMapper;
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final RespondedRequestService respondedRequestService;
    private final EntityManager entityManager;
    private final GenreRepository genreRepository;

    @Override
    public void addNewRequest(CreateNewRequest createNewRequest, Author author) {
        AddingBookRequest request = requestMapper.mapTo(createNewRequest);
        request.setAuthor(author);
        request.setCreateDate(new Date());

        author.getAddingBookRequests().add(request);

        authorRepository.save(author);
    }

    @Override
    public List<RequestDto> getAllRequests(Integer page, Integer pageSize, String field, String order, List<String> authors, List<String> genres) {
        List<AddingBookRequest> requestList = Helper.getBooksOrRequests(entityManager, "bookName", "", page, pageSize, field, order, AddingBookRequest.class, authors, authorRepository, genres, genreRepository);
        return requestList.stream().map(requestMapper::mapTo).toList();
    }

    @Override
    public void acceptRequest(Long id) {
        AddingBookRequest addingBookRequest = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException(id));

        CreateNewBook createNewBook = CreateNewBook
                .builder()
                .name(addingBookRequest.getBookName())
                .isbn(addingBookRequest.getBookIsbn())
                .author(addingBookRequest.getAuthor())
                .genreName(addingBookRequest.getGenreName())
                .page(addingBookRequest.getPage())
                .description(addingBookRequest.getDescription())
                .photoURL(addingBookRequest.getPhotoURL())
                .build();

        bookService.createNewBook(createNewBook);

        Author author = addingBookRequest.getAuthor();
        author.getBooks().add(bookMapper.mapTo(bookService.findByName(addingBookRequest.getBookName())));

        removeRequestAndCreateRespond(author, addingBookRequest, RespondType.ACCEPTED);
    }

    @Override
    public void denyRequest(Long id) {
        AddingBookRequest addingBookRequest = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException(id));

        Author author = addingBookRequest.getAuthor();
        removeRequestAndCreateRespond(author, addingBookRequest, RespondType.DENIED);
    }

    @Override
    public Long getCount() {
        return requestRepository.count();
    }

    private void removeRequestAndCreateRespond(Author author, AddingBookRequest addingBookRequest, RespondType type) {
        author.getAddingBookRequests().remove(addingBookRequest);
        authorRepository.save(author);

        respondedRequestService.create(addingBookRequest, author, type);
    }
}
