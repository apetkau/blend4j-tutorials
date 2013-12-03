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
		// TODO 1.2: Place Galaxy URL here
		// This changes depending on how Galaxy is setup, but should look similar to below
		String galaxyURL = "http://localhost";
		
		// TODO 1.3: Place Galaxy Admin API Key here
		// This changes depending on how Galaxy is setup, but should look similar to below
		// This must be a key of a user setup within the "admin_users" parameter of the universe_wsgi.ini file
		String apiKey = "eef2de0463a9d6f5e2b609d57d09a6c7";
		
		GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(galaxyURL, apiKey);
		
		// create Galaxy Library
		LibrariesClient librariesClient = galaxyInstance.getLibrariesClient();
		Library library = new Library("Question1");
		Library persistedLibrary = librariesClient.createLibrary(library);
		System.out.println("Created Library " + persistedLibrary.getName() + " with id=" + persistedLibrary.getId());
		
		// TODO 1.4: Add path to 'src/main/resources/test.fasta' file here
		// You can either use the full path, or use the below command to get the path
		//	(assumes test.fasta within the same package as Blend4JQuestion1).
		URI path = Blend4JQuestion1.class.getResource("test.fasta").toURI();
		File fileToUpload = new File(path);
		
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
