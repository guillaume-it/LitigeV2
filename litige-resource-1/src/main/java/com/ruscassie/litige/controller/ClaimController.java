package com.ruscassie.litige.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
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
import com.ruscassie.litige.service.ClaimService;
import com.ruscassie.litige.service.StorageService;
import com.ruscassie.litige.service.UserService;

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
	private UserService userService;

	@PreAuthorize("hasAuthority('can_create_claim')")
	@PostMapping()
	public ResponseEntity<Claim> create(@RequestBody final Claim claim) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		final Optional<com.ruscassie.litige.dto.User> userDto = userService.findByEmail(auth.getName());
		claim.setRequerant(userDto.get());
		return new ResponseEntity<Claim>(claimService.save(claim), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('can_delete_claim')")
	@RequestMapping(value = "{id}", method = RequestMethod.DELETE)
	public void delete(@PathVariable(value = "id") final Long id) {
		claimService.delete(id);
	}

	@PreAuthorize("hasAuthority('can_read_claim')")
	@GetMapping(path = "/{id}")
	public ResponseEntity<Claim> findOne(@PathVariable final long id) {
		return new ResponseEntity<Claim>(claimService.findOne(id), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('can_read_claim')")
	@GetMapping(value = "")
	public ResponseEntity<Page<Claim>> list(final Pageable pageable) {
		return new ResponseEntity<Page<Claim>>(claimService.findAll(pageable), HttpStatus.OK);
	}

	@PreAuthorize("hasAuthority('can_update_claim')")
	@PutMapping()
	public ResponseEntity<Claim> update(@RequestBody final Claim claim) {
		final Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		final Optional<com.ruscassie.litige.dto.User> userDto = userService.findByEmail(auth.getName());
		claim.setRequerant(userDto.get());
		return new ResponseEntity<Claim>(claimService.save(claim), HttpStatus.OK);
	}

	@PostMapping("/upload-file")
	@ResponseBody
	public ResponseEntity<FileInformation> uploadFile(@RequestParam("file") final MultipartFile file,
			@RequestParam("idClaim") final Long idClaim) {

		final FileInformation fileInformation = storageService.create(file);
		claimService.addFileInformation(idClaim, fileInformation);

		return new ResponseEntity<FileInformation>(fileInformation, HttpStatus.OK);
	}

}
