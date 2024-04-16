package hello.board.service;

import hello.board.entity.BoardFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class BoardFileServiceImpl implements BoardFileService {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String filename) {
        return fileDir + filename;
    }

    @Override
    public List<BoardFile> saveFiles(List<MultipartFile> imageFiles) throws IOException {

        List<BoardFile> boardFiles = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            if (!imageFile.isEmpty()) {
                boardFiles.add(saveFile(imageFile));
            }
        }

        return boardFiles;
    }

    public BoardFile saveFile(MultipartFile imageFile) throws IOException {
        if (imageFile.isEmpty()) {
            return null;
        }

        String originalFilename = imageFile.getOriginalFilename();
        String storeFileName = createStoreFileName(originalFilename);
        imageFile.transferTo(new File(getFullPath(storeFileName)));

        return new BoardFile(originalFilename, storeFileName);

    }

    private String createStoreFileName(String originalFilename) {
        String uuid = UUID.randomUUID().toString();
        String ext = extractExt(originalFilename);
        return uuid + "." + ext;
    }

    private String extractExt(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
