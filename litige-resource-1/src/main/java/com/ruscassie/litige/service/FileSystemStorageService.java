package com.ruscassie.litige.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.stream.Stream;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.ruscassie.litige.config.StorageProperties;
import com.ruscassie.litige.exception.FileNotFoundException;
import com.ruscassie.litige.exception.StorageException;

@Service
public class FileSystemStorageService implements StorageService {

	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(final StorageProperties properties) {
		this.rootLocation = Paths.get(properties.getLocation());
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	@PostConstruct
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		} catch (final IOException e) {
			throw new StorageException("Could not initialize storage location", e);
		}
	}

	@Override
	public Path load(final String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1).filter(path -> !path.equals(this.rootLocation))
					.map(this.rootLocation::relativize);
		} catch (final IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Resource loadAsResource(final String filename) {
		try {
			final Path file = load(filename);
			final Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			} else {
				throw new FileNotFoundException("Could not read file: " + filename);
			}
		} catch (final MalformedURLException e) {
			throw new FileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public String store(final MultipartFile file) {
		final String filename = StringUtils.cleanPath(file.getOriginalFilename());
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file " + filename);
			}
			if (filename.contains("..")) {
				// This is a security check
				throw new StorageException(
						"Cannot store file with relative path outside current directory " + filename);
			}
			try (InputStream inputStream = file.getInputStream()) {
				// Files.copy(inputStream, this.rootLocation.resolve(filename),
				// StandardCopyOption.REPLACE_EXISTING);

				Files.copy(inputStream, this.rootLocation.resolve(
						"/Users/Guillaume/Developpement/Git/LitigeV2/litige-resource-1/src/main/resources/" + filename),
						StandardCopyOption.REPLACE_EXISTING);
			}
		} catch (final IOException e) {
			throw new StorageException("Failed to store file " + filename, e);
		}

		return filename;
	}
}
