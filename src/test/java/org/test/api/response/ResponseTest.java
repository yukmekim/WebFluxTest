package org.test.api.response;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResponseTest {

    @Test
    public void responseTest() {
        Response response = Response.builder()
                .status(StatusEnum.OK)
                .message("success")
                .build();

        System.out.println(response.getMessage());
        System.out.println(response.getStatus());
    }
}