package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.mapper.RespondedRequestMapper;
import tr.com.obss.jip.model.Author;
import tr.com.obss.jip.model.Request;
import tr.com.obss.jip.model.RespondType;
import tr.com.obss.jip.model.RespondedRequest;
import tr.com.obss.jip.service.AuthorService;
import tr.com.obss.jip.service.RespondedRequestService;

@Slf4j
@RequiredArgsConstructor
@Service
public class RespondedRequestServiceImpl implements RespondedRequestService {
    private final RespondedRequestMapper respondedRequestMapper;
    private final AuthorService authorService;

    @Override
    public void create(Request request, Author author, RespondType type) {
        RespondedRequest respondedRequest = respondedRequestMapper.mapTo(request);
        respondedRequest.setRespond(type);

        author.getRespondedRequests().add(respondedRequest);
        authorService.save(author);
    }
}
