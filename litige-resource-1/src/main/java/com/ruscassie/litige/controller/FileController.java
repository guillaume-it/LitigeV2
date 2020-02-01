package com.ruscassie.litige.controller;

import java.util.stream.Collectors;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.ruscassie.litige.service.StorageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/files")
@Slf4j
@Validated
public class FileController {

	private final StorageService storageService;

	public FileController(final StorageService storageService) {
		this.storageService = storageService;
	}

	@GetMapping("/download/{filename:.+}")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@PathVariable final String filename) {

		final Resource resource = storageService.loadAsResource(filename);

		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
				.body(resource);
	}

	@GetMapping("/")
	public ResponseEntity<String> listAllFiles(final Model model) {

		model.addAttribute("files",
				storageService.loadAll().map(path -> ServletUriComponentsBuilder.fromCurrentContextPath()
						.path("/download/").path(path.getFileName().toString()).toUriString())
						.collect(Collectors.toList()));

		return new ResponseEntity<String>("listFiles", HttpStatus.OK);
	}

//	@PostMapping("/upload-file")
//	@ResponseBody
//	public ResponseEntity<FileInformation> uploadFile(@RequestParam("file") final MultipartFile file) {
//		final FileInformation fileInformation = storageService.store(file);
//
//		return new ResponseEntity<FileInformation>(fileInformation, HttpStatus.OK);
//	}

}