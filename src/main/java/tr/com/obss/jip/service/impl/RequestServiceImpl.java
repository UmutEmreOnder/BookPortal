package tr.com.obss.jip.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.mapper.RequestMapper;
import tr.com.obss.jip.model.Request;
import tr.com.obss.jip.repository.RequestRepository;
import tr.com.obss.jip.service.RequestService;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RequestServiceImpl implements RequestService {
    private final RequestRepository requestRepository;
    private final RequestMapper requestMapper;


    @Override
    public void createRequest(CreateNewRequest createNewRequest) {
        requestRepository.save(requestMapper.mapTo(createNewRequest));
    }
}
