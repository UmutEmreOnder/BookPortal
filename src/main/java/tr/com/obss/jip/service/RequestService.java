package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.RequestDto;

import java.util.List;

public interface RequestService {
    List<RequestDto> getAllRequests();

    void acceptRequest(Long id);

    void denyRequest(Long id);
}
