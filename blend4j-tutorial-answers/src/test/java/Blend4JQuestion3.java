import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.github.jmchilton.blend4j.galaxy.GalaxyInstance;
import com.github.jmchilton.blend4j.galaxy.GalaxyInstanceFactory;
import com.github.jmchilton.blend4j.galaxy.LibrariesClient;
import com.github.jmchilton.blend4j.galaxy.beans.FileLibraryUpload;
import com.github.jmchilton.blend4j.galaxy.beans.Library;
import com.github.jmchilton.blend4j.galaxy.beans.LibraryContent;
import com.github.jmchilton.galaxybootstrap.BootStrapper;
import com.github.jmchilton.galaxybootstrap.BootStrapper.GalaxyDaemon;
import com.github.jmchilton.galaxybootstrap.DownloadProperties;
import com.github.jmchilton.galaxybootstrap.GalaxyData;
import com.github.jmchilton.galaxybootstrap.GalaxyData.User;
import com.github.jmchilton.galaxybootstrap.GalaxyProperties;
import com.sun.jersey.api.client.ClientResponse;

public class Blend4JQuestion3
{
	private static File fileToUpload = null;
	
	private static GalaxyInstance galaxyInstance;
	
	private static GalaxyDaemon galaxyDaemon;
	
	@BeforeClass
	public static void setupGalaxy() throws IOException, URISyntaxException
	{
	    BootStrapper bootStrapper = new BootStrapper(DownloadProperties.forGalaxyCentral());
	    bootStrapper.setVerbose(true); // this method not in main version, so don't try to use it
	    bootStrapper.setupGalaxy();
	    final GalaxyProperties galaxyProperties = 
	      new GalaxyProperties()
	            .assignFreePort()
	            .configureNestedShedTools();
	    final GalaxyData galaxyData = new GalaxyData();
	    final User adminUser = new User("admin@localhost");
	    galaxyData.getUsers().add(adminUser);
	    galaxyProperties.setAdminUser("admin@localhost");
	    String adminAPIKey = adminUser.getApiKey();
	    
	    galaxyProperties.setAppProperty("allow_library_path_paste", "true");
	    galaxyDaemon = bootStrapper.run(galaxyProperties, galaxyData);
	    
	    if (!galaxyDaemon.waitForUp())
	    {
	    	fail("Could not start Galaxy for tests");
	    }
	    
	    int galaxyPort = galaxyProperties.getPort();
	    String galaxyURL = "http://localhost:" + galaxyPort + "/";
	    
	    setupGalaxyInstance(adminAPIKey, galaxyURL);
	}
	
	@AfterClass
	public static void destroyGalaxy()
	{
		galaxyDaemon.stop();
		galaxyDaemon.waitForDown();
	}
	
	private static void setupGalaxyInstance(String apiKey, String galaxyURL) throws URISyntaxException
	{
		// TODO 1: Setup galaxy instance with the apiKey and URL from
		// the Galaxy bootstrapper
		galaxyInstance = GalaxyInstanceFactory.get(galaxyURL, apiKey);
		
		URI path = Blend4JQuestion3.class.getResource("test.fasta").toURI();
		fileToUpload = new File(path);
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

		assertEquals(ClientResponse.Status.OK, uploadResponse.getClientResponseStatus());
		assertTrue(fileUploadedTo(fileToUpload.getName(), libraryName));
	}
}
