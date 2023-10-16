package simlabapp.service;

import simlabapp.dto.request.LoginAccountRequest;
import simlabapp.dto.response.LoginAccountResponse;

public interface AccountService {

    LoginAccountResponse login(LoginAccountRequest request);

}
