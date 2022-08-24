package tr.com.obss.jip.service;

import tr.com.obss.jip.dto.RequestDto;
import tr.com.obss.jip.dto.create.CreateNewRequest;
import tr.com.obss.jip.model.Author;

import java.util.List;

public interface RequestService {
    void addNewRequest(CreateNewRequest createNewRequest, Author author);

    void acceptRequest(Long id);

    void denyRequest(Long id);

    Long getCount();

    List<RequestDto> getAllRequests(Integer page, Integer pageSize, String field, String order, List<String> authors, List<String> genres);
}
