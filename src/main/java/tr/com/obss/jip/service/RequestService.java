package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;

import java.util.List;

public interface RequestService {
    void createRequest(CreateNewRequest createNewRequest);

    List<RequestDto> getAllRequests();

    void acceptRequest(Long id);
}
