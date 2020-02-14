package com.ruscassie.litige.service;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ruscassie.litige.config.StorageProperties;
import com.ruscassie.litige.entity.FileInformation;
import com.ruscassie.litige.exception.FileNotFoundException;
import com.ruscassie.litige.exception.StorageException;
import com.ruscassie.litige.mapper.FileInformationMapper;
import com.ruscassie.litige.repository.FileInformationRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class StorageService {

	@Autowired
	private FileInformationRepository fileInformationRepository;

	@Value("${properties.storage-location}")
	private String storageLocation;

// TODO check if the file exist
	public FileInformation create(final MultipartFile file, final String directory) {
		final FileInformation fileInformation = store(file, directory);
		return fileInformationRepository.save(fileInformation);
	}

	public boolean delete(final FileInformation fileInformation) {
		try {
			Files.delete(new File(fileInformation.getUri()).toPath());
		} catch (final IOException e) {
			this.log.error(e.getMessage(), e);
			return false;
		}
//TODO delete directory if last file
		fileInformationRepository.delete(fileInformation);

		return true;

	}

	public Stream<Path> loadAll() {
		Path rootLocation = Paths.get(this.storageLocation);
		try {
			return Files.walk(rootLocation, 1).filter(path -> !path.equals(rootLocation))
					.map(rootLocation::relativize);
		} catch (final IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	public com.ruscassie.litige.dto.FileInformation loadAsResource(final Long idFile, final Long idClaim) {
		final Optional<FileInformation> fileInformation = fileInformationRepository.findByIdAndClaims_Id(idFile,
				idClaim);

		final File file = new File(fileInformation.get().getUri());

		final Resource resource = new FileSystemResource(file.getPath());
		if (resource.exists() || resource.isReadable()) {
			final com.ruscassie.litige.dto.FileInformation fileDto = FileInformationMapper
					.mapper(fileInformation.get());
			fileDto.setResource(resource);
			return fileDto;
		} else {
			throw new FileNotFoundException("Could not read file: " + fileInformation.get().getName());
		}

	}

	public FileInformation store(final MultipartFile file, final String directory) {
		Path rootLocation = Paths.get(this.storageLocation);

		final String filename = StringUtils.cleanPath(file.getOriginalFilename());
		final String path = rootLocation.toString() + File.separator + directory + File.separator + filename;
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			try {
				Files.createDirectories(new File(rootLocation + File.separator + directory).toPath());
			} catch (final IOException e) {
				throw new StorageException("Could not initialize storage location", e);
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, rootLocation.resolve(path), StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (final IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}

		final FileInformation fileInformation = new FileInformation();
		fileInformation.setContentType(file.getContentType());
		fileInformation.setName(filename);
		fileInformation.setUri(path);
		fileInformation.setSize(file.getSize());

		return fileInformation;
	}
}
