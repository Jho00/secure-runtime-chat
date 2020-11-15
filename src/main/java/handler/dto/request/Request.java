package handler.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Request {
    private String action;
    private String uniqueId;
    private Object data;
}
