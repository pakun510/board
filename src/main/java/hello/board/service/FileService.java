package hello.board.service;

import hello.board.entity.BoardFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface FileService {

    String getFullPath(String filename);

    List<BoardFile> saveBoardFiles(List<MultipartFile> imageFiles) throws IOException;

    String saveFileReturnStoreFileName(MultipartFile imageFile) throws IOException;


}
