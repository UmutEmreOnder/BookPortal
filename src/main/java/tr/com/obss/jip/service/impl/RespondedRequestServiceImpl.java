package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.mapper.RespondedRequestMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.AddingBookRequest;
import tr.com.obss.jip.model.RespondType;
import tr.com.obss.jip.model.RespondedBookRequest;
import tr.com.obss.jip.repository.AuthorRepository;
import tr.com.obss.jip.service.RespondedRequestService;

@Slf4j
@RequiredArgsConstructor
@Service
public class RespondedRequestServiceImpl implements RespondedRequestService {
    private final RespondedRequestMapper respondedRequestMapper;
    private final AuthorRepository authorRepository;

    @Override
    public void create(AddingBookRequest addingBookRequest, Author author, RespondType type) {
        RespondedBookRequest respondedBookRequest = respondedRequestMapper.mapTo(addingBookRequest);
        respondedBookRequest.setRespond(type);

        author.getRespondedBookRequests().add(respondedBookRequest);
        authorRepository.save(author);
    }
}
