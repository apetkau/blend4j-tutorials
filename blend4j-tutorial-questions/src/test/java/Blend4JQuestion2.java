import static org.junit.Assert.*;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;

import com.github.jmchilton.blend4j.galaxy.GalaxyInstance;
import com.github.jmchilton.blend4j.galaxy.GalaxyInstanceFactory;
import com.github.jmchilton.blend4j.galaxy.LibrariesClient;
import com.github.jmchilton.blend4j.galaxy.beans.FileLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.sun.jersey.api.client.ClientResponse;

public class Blend4JQuestion2
{
	private GalaxyInstance galaxyInstance = null;
	private File fileToUpload = null;
	
	@Before
	public void setup() throws URISyntaxException
	{

	}
	
	private Map<String,LibraryContent> createLibraryContentMap(List<LibraryContent> libraryContents)
	{
		Map<String,LibraryContent> map = new HashMap<String,LibraryContent>();
		for (LibraryContent content : libraryContents)
		{
			map.put(content.getName(), content);
		}
		return map;
	}
	
	/**
	 * Checks that the passed fileName has been uploaded to the passed library
	 * @param fileName  The file to check against.
	 * @param libraryName  The library name to check against.
	 * @return  True if the file was found within the passed Galaxy library, false otherwise.
	 */
	private boolean fileUploadedTo(String fileName, String libraryName)
	{
		Library matchingLibrary = null;
		List<LibraryContent> libraryContentsList = null;
		Map<String,LibraryContent> libraryContentsMap = null;
		
		// search for matching Library object to libraryName
		LibrariesClient librariesClient = galaxyInstance.getLibrariesClient();
		List<Library> libraries = librariesClient.getLibraries();
		for (Library curr : libraries)
		{
			if (libraryName.equals(curr.getName()))
			{
				matchingLibrary = curr;
			}
		}
		
		// gets all contents stored within this library
		// converts to HashMap mapping file name to Galaxy library object since I find it easier to work with
		libraryContentsList = galaxyInstance.getLibrariesClient().getLibraryContents(matchingLibrary.getId());
		libraryContentsMap = createLibraryContentMap(libraryContentsList);
		
		return libraryContentsMap.containsKey("/" + fileName);
	}
	
	@Test
	public void testUploadFile() throws URISyntaxException
	{
		// TODO 1: Move setup code to setup() method and use the given class variables
		String galaxyURL = "http://localhost";
		String apiKey = "insert_key_here";
		GalaxyInstance galaxyInstance = GalaxyInstanceFactory.get(galaxyURL, apiKey);
		
		URI path = Blend4JQuestion2.class.getResource("test.fasta").toURI();
		File fileToUpload = new File(path);
		
		// create Galaxy Library
		String libraryName = "Question2" + System.currentTimeMillis(); // change library name on each run
		LibrariesClient librariesClient = galaxyInstance.getLibrariesClient();
		Library library = new Library(libraryName);
		Library persistedLibrary = librariesClient.createLibrary(library);
		
		// upload file to Library
		LibraryContent rootFolder = librariesClient.getRootFolder(persistedLibrary.getId());
		FileLibraryUpload upload = new FileLibraryUpload();
		upload.setFolderId(rootFolder.getId());
		
		upload.setFile(fileToUpload);
		upload.setName(fileToUpload.getName());
		
		ClientResponse uploadResponse = librariesClient.uploadFile(persistedLibrary.getId(), upload);
		
		// TODO 2: Instead of printing the success status, add an 'assertEquals' statement to verify 
		//  the appropriate response
		
		// TODO 3: Instead of visually inspecting the Galaxy data libraries to make
		//	sure the file has been uploaded, use the fileUploadedTo() method and an assertion
		// 	to verify the file has been uploaded.
	}
}
