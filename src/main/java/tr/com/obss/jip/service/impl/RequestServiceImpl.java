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
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Request;
import tr.com.obss.jip.repository.RequestRepository;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.BookService;
import tr.com.obss.jip.service.RequestService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;
    private final AuthorService authorService;
    private final BookService bookService;
    private final BookMapper bookMapper;


    @Override
    public void createRequest(CreateNewRequest createNewRequest) {
        requestRepository.save(requestMapper.mapTo(createNewRequest));
    }

    @Override
    public List<RequestDto> getAllRequests() {
        Iterable<Request> requests = requestRepository.findAll();
        List<RequestDto> retList = new ArrayList<>();

        requests.forEach(request -> retList.add(requestMapper.mapTo(request)));

        return retList;
    }

    @Override
    public void acceptRequest(Long id) {
        Request request = requestRepository.findById(id).orElseThrow(() -> new RequestNotFoundException(id));

        CreateNewBook createNewBook = CreateNewBook
                .builder()
                .name(request.getBookName())
                .isbn(request.getBookIsbn())
                .build();

        bookService.createNewBook(createNewBook);

        Author author = request.getAuthor();

        author.getBooks().add(bookMapper.mapTo(bookService.findByName(request.getBookName())));

        authorService.save(author);

        // requestRepository.delete(request);
    }
}
