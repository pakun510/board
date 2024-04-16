package hello.board.service;

import hello.board.entity.BoardFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface BoardFileService {

    List<BoardFile> saveFiles(List<MultipartFile> imageFiles) throws IOException;

}
