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
import tr.com.obss.jip.repository.RequestRepository;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.RequestService;
import tr.com.obss.jip.service.RespondedRequestService;

import java.util.ArrayList;
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

    @Override
    public void addNewRequest(CreateNewRequest createNewRequest, Author author) {
        createNewRequest.setAuthor(author);
        AddingBookRequest request = requestMapper.mapTo(createNewRequest);
        request.setCreateDate(new Date());

        author.getAddingBookRequests().add(request);

        authorRepository.save(author);
    }
    @Override
    public List<RequestDto> getAllRequests() {
        Iterable<AddingBookRequest> requests = requestRepository.findAll();
        List<RequestDto> retList = new ArrayList<>();

        requests.forEach(request -> retList.add(requestMapper.mapTo(request)));

        return retList;
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

    private void removeRequestAndCreateRespond(Author author, AddingBookRequest addingBookRequest, RespondType type) {
        author.getAddingBookRequests().remove(addingBookRequest);
        authorRepository.save(author);
        requestRepository.delete(addingBookRequest);

        respondedRequestService.create(addingBookRequest, author, type);
    }
}
