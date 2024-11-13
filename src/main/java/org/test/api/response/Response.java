package org.test.api.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
@AllArgsConstructor
public class Response {
    private StatusEnum status;
    private String message;
    private Object data;

    public Response () {
        this.status = StatusEnum.BAD_REQUEST;
        this.message = null;
        this.data = null;
    }
}
