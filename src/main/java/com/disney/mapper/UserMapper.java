package com.disney.mapper;

import com.disney.dto.response.UserEmailResponse;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserEmailResponse map(String name) {
        UserEmailResponse userEmailResponse = new UserEmailResponse();
        userEmailResponse.setName(name);
        return userEmailResponse;
    }
}
