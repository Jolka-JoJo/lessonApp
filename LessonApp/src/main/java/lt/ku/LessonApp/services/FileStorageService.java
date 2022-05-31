package lt.ku.LessonApp.services;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.core.io.Resource;

import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class FileStorageService {
	private final Path storageLocation;

	public FileStorageService() {
		this.storageLocation = Paths.get("uploads");
	}
	
	public void store(MultipartFile file, String filename) {
		try {
			Path targetFile = this.storageLocation.resolve(filename).normalize().toAbsolutePath();
			InputStream input = file.getInputStream();
			System.out.println("Filename " + filename);
			
			Files.copy(input, targetFile, StandardCopyOption.REPLACE_EXISTING);
		}
		catch (IOException e) {
			System.out.print("Įkeliant failą gauta klaida");
			e.printStackTrace();
		}
	}
	
	public void store(MultipartFile file) {
		this.store(file, file.getOriginalFilename());
	}
	
	public Resource loadFile(String filename) {
		try {
			Path filePath = this.storageLocation.resolve(filename).normalize();
			Resource resource=new UrlResource(filePath.toUri());
			if(!Files.exists(filePath)) return null;
			return resource;
		}
		catch (IOException e) {
			System.out.println("Įvyko klaida atvaizduojant");
		}
		return null;
	}
	
	public String getContentType(String filename) {
		try{
			Path filePath = this.storageLocation.resolve(filename).normalize();
			return Files.probeContentType(filePath);
		}
		catch(IOException e) {
			System.out.println("Ivyko klaida atvaizduojant");
			e.printStackTrace();
		}
		return null;
	}
}
