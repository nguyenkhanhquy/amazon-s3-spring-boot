package com.aws.s3.controller;

import com.aws.s3.util.S3Util;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.io.InputStream;

@Controller
public class S3Controller {

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
    public String handleFileUpload(@RequestParam("file") MultipartFile multipart, Model model) {
        String fileName = multipart.getOriginalFilename();
        String message;

        // Kiểm tra nếu fileName là null hoặc không có phần mở rộng
        if (fileName == null || !fileName.contains(".")) {
            message = "Invalid file format";
        } else {
            try (InputStream inputStream = multipart.getInputStream()) {
                // Tạo tên file mới với phần mở rộng từ file gốc
                String fileExtension = fileName.substring(fileName.lastIndexOf('.'));
                String newFileName = S3Util.AWS_URL_FOLDER + "default-images-demo" + fileExtension;

                // Upload tệp lên S3
                S3Util.uploadFile(newFileName, inputStream);

                message = "Your file has been uploaded successfully";
            } catch (IOException ex) {
                message = "Error uploading file: " + ex.getMessage();
            }
        }

        model.addAttribute("message", message);
        return "message_page";
    }

    @PostMapping("/delete")
    public String handleDeleteFileUpload(String fileName, Model model){
        String message;

        // Kiểm tra tính hợp lệ của tên tệp
        if (fileName == null || fileName.isEmpty()) {
            message = "Invalid file name";
        } else {
            try {
                // Xóa tệp từ S3
                S3Util.deleteFile(S3Util.AWS_URL_FOLDER + fileName);
                message = "Your file has been deleted successfully";
            } catch (Exception ex) {
                // Bắt ngoại lệ tổng quát và ghi log lỗi
                message = "Error deleting file: " + ex.getMessage();
            }
        }

        model.addAttribute("message", message);
        return "message_page";
    }

}
