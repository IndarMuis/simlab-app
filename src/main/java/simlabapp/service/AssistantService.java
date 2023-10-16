package simlabapp.service;

import simlabapp.dto.request.CreateAssistantRequest;

public interface AssistantService {

    void register(CreateAssistantRequest request);

}
