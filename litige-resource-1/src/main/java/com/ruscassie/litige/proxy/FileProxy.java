//package com.ruscassie.litige.proxy;
//
//import com.ruscassie.litige.dto.FileInformation;
//import com.ruscassie.litige.dto.User;
//import org.springframework.cloud.netflix.ribbon.RibbonClient;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.core.io.Resource;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.ResponseBody;
//import org.springframework.web.multipart.MultipartFile;
//
//@FeignClient(name = "zuul-server")
//@RibbonClient(name = "file-server")
//public interface FileProxy {
//
//    @PostMapping(value = "/files/upload-file")
//    @ResponseBody
//    FileInformation uploadFile(@RequestParam("file") final MultipartFile file, @RequestParam("directory") final String directory);
//
//    @DeleteMapping("/delete-file")
//    @ResponseBody
//    boolean delete(com.ruscassie.litige.entity.FileInformation fileInformation);
//
//    @PostMapping("/download-file")
//    @ResponseBody
//    ResponseEntity<Resource> downloadFile(@RequestParam("idFile") final Long idFile,
//                                          @RequestParam("idClaim") final Long idClaim);
//
//}
