import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import com.github.jmchilton.blend4j.galaxy.GalaxyInstance;
import com.github.jmchilton.blend4j.galaxy.GalaxyInstanceFactory;
import com.github.jmchilton.blend4j.galaxy.LibrariesClient;
import com.github.jmchilton.blend4j.galaxy.beans.FileLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.sun.jersey.api.client.ClientResponse;

public class Blend4JQuestion1
{
	public static void main(String[] args) throws URISyntaxException
	{
		// TODO 2: Place Galaxy URL here
		String galaxyURL = "";
		
		// TODO 3: Place Galaxy Admin API Key here
		String apiKey = "";
		
		GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(galaxyURL, apiKey);
		
		// create Galaxy Library
		LibrariesClient librariesClient = galaxyInstance.getLibrariesClient();
		Library library = new Library("Question1");
		Library persistedLibrary = librariesClient.createLibrary(library);
		System.out.println("Created Library " + persistedLibrary.getName() + " with id=" + persistedLibrary.getId());
		
		// TODO 4: Add path to 'src/main/resources/test.fasta' file here
		File fileToUpload = new File("");
		
		// upload file to Library
		LibraryContent rootFolder = librariesClient.getRootFolder(persistedLibrary.getId());
		FileLibraryUpload upload = new FileLibraryUpload();
		upload.setFolderId(rootFolder.getId());
		
		upload.setFile(fileToUpload);
		upload.setName(fileToUpload.getName());
		
		ClientResponse uploadResponse = librariesClient.uploadFile(persistedLibrary.getId(), upload);
		if (ClientResponse.Status.OK.equals(uploadResponse.getClientResponseStatus()))
		{
			System.out.println("Successfully uploaded " + fileToUpload.getName());
		}
		else
		{
			System.out.println("Could not upload " + fileToUpload.getName());
		}		
	}
}
