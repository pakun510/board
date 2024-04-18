package hello.board.controller;

import org.springframework.stereotype.Component;

@Component
public class FileValidator {

    public boolean isSupportedContentType(String contentType) {
        return contentType.equals("image/png")
                || contentType.equals("image/jpg")
                || contentType.equals("image/jpeg");
    }

}
