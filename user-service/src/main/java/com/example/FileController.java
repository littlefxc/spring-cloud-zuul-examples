package com.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

/**
 * 文件上传和下载
 *
 * @author fengxuechao
 * @date 2019-08-28
 */
@Slf4j
@RestController
@RequestMapping("file")
public class FileController {

    private String folder = "/Users/fengxuechao/WorkSpace/IdeaProjects/spring-cloud-zuul-examples/file";

    /**
     * 文件上传
     *
     * @param file
     * @return
     * @throws IOException
     */
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {

        log.info("参数名 {}", file.getName());
        log.info("文件原始名 {}", file.getOriginalFilename());
        log.info("文件大小 {}", file.getSize());

        File localFile = new File(folder, System.currentTimeMillis() + file.getOriginalFilename());

        file.transferTo(localFile);

        return new FileInfo(localFile.getAbsolutePath());
    }

    /**
     * 文件下载
     *
     * @param filename
     * @param request
     * @param response
     * @throws Exception
     */
    @GetMapping
    public void download(@RequestParam String filename, HttpServletRequest request, HttpServletResponse response) throws Exception {

        try (InputStream inputStream = new FileInputStream(new File(folder, filename));
             OutputStream outputStream = response.getOutputStream()) {

            response.setContentType("application/x-download");
            // 以指定的文件名下载文件
            response.addHeader("Content-Disposition", "attachment;filename=" + filename);
            // 将文件的输入流拷贝到输出流，将文件内容响应到输出流中去
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }

    }
}
