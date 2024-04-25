package com.aws.s3;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;

@Controller
public class MainController {

    @GetMapping("/")
    public String showHomePage() {
        return "home_page";
    }

    @GetMapping("/upload")
    public String showUploadPage() {
        return "upload_page";
    }

    @GetMapping("/delete")
    public String showDeletePage() {
        return "delete_page";
    }

    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile multipart,
                                   Model model) {
        String fileName = multipart.getOriginalFilename();

        String message;

        try {
            String newFileName = S3Util.AWS_URL_FOLDER + "new-name" + fileName.substring(fileName.lastIndexOf('.'));
            S3Util.uploadFile(newFileName, multipart.getInputStream());
            message = "Your file has been uploaded successfully";
        } catch (IOException ex) {
            message = "Error uploading file: " + ex.getMessage();
        }

        model.addAttribute("message", message);

        return "message_page";
    }

    @PostMapping("/delete")
    public String handleDeleteFileUpload(String fileName,
                                         Model model){
        String message;
        try {
            S3Util.deleteFile(S3Util.AWS_URL_FOLDER + fileName);
            message = "Your file has been deleted successfully";
        } catch (Exception ex) {
            message = "Error deleting file: " + ex.getMessage();
        }

        model.addAttribute("message", message);

        return "message_page";
    }

}
