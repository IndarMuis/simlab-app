package simlabapp.service;

import org.springframework.web.multipart.MultipartFile;
import simlabapp.dto.request.CreateAssistantRequest;

import java.io.IOException;

public interface AssistantService {

    void register(CreateAssistantRequest request);

    void uploadImageProfile(MultipartFile image) throws IOException;

}
