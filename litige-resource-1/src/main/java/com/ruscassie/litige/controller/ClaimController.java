package com.ruscassie.litige.controller;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.ruscassie.litige.mapper.UserMapper;
import com.ruscassie.litige.proxy.UserProxy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ruscassie.litige.dto.Claim;
import com.ruscassie.litige.dto.FileInformation;
import com.ruscassie.litige.dto.User;
import com.ruscassie.litige.mapper.ClaimMapper;
import com.ruscassie.litige.mapper.FileInformationMapper;
import com.ruscassie.litige.service.ClaimService;
import com.ruscassie.litige.service.StorageService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/litiges")
@Slf4j
@Validated
public class ClaimController {
	@Autowired
	private StorageService storageService;

	@Autowired
	private ClaimService claimService;

	@Autowired
	private UserProxy userProxy;

	@PreAuthorize("hasAuthority('can_create_claim')")
	@PostMapping()
	public ResponseEntity<Claim> create(@RequestBody final Claim claim) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		final User user = userProxy.findByEmail(auth.getName());
		final com.ruscassie.litige.entity.Claim entity = ClaimMapper.mapper(claim);
		entity.setCreation(ZonedDateTime.now());
		//entity.setClaimant(UserMapper.mapper(user));
		entity.setClaimantId(user.getId());

		return new ResponseEntity<Claim>(ClaimMapper.mapper(claimService.save(entity)), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('can_delete_claim')")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") final Long id) {
		claimService.delete(id);
	}

	@PreAuthorize("hasAuthority('can_delete_file_information')")
	@DeleteMapping("/delete-file")
	@ResponseBody
	public ResponseEntity<FileInformation> deleteFile(@RequestParam("idFileInformation") final Long idFileInformation,
			@RequestParam("idClaim") final Long idClaim) {

		if (claimService.removeFileInformation(idClaim, idFileInformation)) {
			return new ResponseEntity<FileInformation>(HttpStatus.OK);
		}
		return new ResponseEntity<FileInformation>(HttpStatus.NOT_MODIFIED);
	}

	@PostMapping("/download-file")
	@ResponseBody
	public ResponseEntity<Resource> downloadFile(@RequestParam("idFile") final Long idFile,
			@RequestParam("idClaim") final Long idClaim) {

		final FileInformation fileInformation = storageService.loadAsResource(idFile, idClaim);
		// "attachment; filename=\"" + resource.getFilename() + "\""

		final HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.add(HttpHeaders.CONTENT_TYPE, fileInformation.getContentType());
		responseHeaders.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; File-Name=" + fileInformation.getName());
		// responseHeaders.add("Cache-Control", "no-cache, no-store, must-revalidate");
//		responseHeaders.add("Pragma", "no-cache");
//		responseHeaders.add("Expires", "0");// .contentType(MediaType.parseMediaType("application/octet-stream")

		return ResponseEntity.ok().headers(responseHeaders).contentLength(fileInformation.getSize())
				.body(fileInformation.getResource());
//		return ResponseEntity.ok()
//				.header(HttpHeaders.CONTENT_DISPOSITION, HttpHeaders.CONTENT_TYPE)
//				.body(resource);
	}

	// @PreAuthorize("hasAuthority('can_read_claim')")
	@GetMapping(path = "/{id}")
	public ResponseEntity<Claim> findOne(@PathVariable final long id) {
		final Optional<com.ruscassie.litige.entity.Claim> claim = claimService.findOne(id);
		if (claim.isPresent()) {
			final Claim claimDto = ClaimMapper.mapper(claim.get());
			claimDto.setFileInformations(claim.get().getFileInformations().stream().map(file -> {
				return FileInformationMapper.mapper(file);
			}).collect(Collectors.toList()));
			return new ResponseEntity<Claim>(claimDto, HttpStatus.OK);
		} else
			return new ResponseEntity<Claim>(HttpStatus.NOT_FOUND);
	}

	@PreAuthorize("hasAuthority('can_read_claim')")
	@GetMapping(value = "")
	public ResponseEntity<Page<Claim>> list(final Pageable pageable) {

		final Page<com.ruscassie.litige.entity.Claim> pageClaim = claimService.findAll(pageable);

		final List<Claim> claims = pageClaim.getContent().stream().map(claim -> {
			return ClaimMapper.mapper(claim);
		}).collect(Collectors.toList());

		final Page<Claim> pageableDto = new PageImpl<>(claims, pageClaim.getPageable(), pageClaim.getTotalElements());

		return new ResponseEntity<>(pageableDto, HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('can_update_claim')")
	@PutMapping()
	public ResponseEntity<Claim> update(@RequestBody final Claim claim) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		final User user = userProxy.findByEmail(auth.getName());
		final com.ruscassie.litige.entity.Claim entity = ClaimMapper.mapper(claim);
		//entity.setClaimant(UserMapper.mapper(user));
		entity.setClaimantId(user.getId());

		return new ResponseEntity<>(ClaimMapper.mapper(claimService.save(entity)), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('can_update_file_information')")
	@PostMapping("/upload-file")
	@ResponseBody
	public ResponseEntity<FileInformation> uploadFile(@RequestParam("file") final MultipartFile file,
			@RequestParam("idClaim") final Long idClaim) {

		final Optional<com.ruscassie.litige.entity.FileInformation> fileInformation = claimService
				.addFileInformation(idClaim, file);
		if (fileInformation.isPresent()) {
			return new ResponseEntity<>(FileInformationMapper.mapper(fileInformation.get()),
					HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	}
}
