package mmortari;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Path("/hello")
public class GreetingResource {

    @ConfigProperty(name = "PVDF_DIRECTORY", defaultValue = "/tmp")
    String dirName;

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() throws Exception {
        StringBuilder sb = new StringBuilder(dirName);
        sb.append("\n");

        File dir = new File(dirName);
        sb.append("Usable: "+dir.getUsableSpace() + ", Free: "+dir.getFreeSpace() + ", Total: "+dir.getTotalSpace()).append("\n");
        sb.append("\n");
        ProcessBuilder builder = new ProcessBuilder("df", "-h", dirName);
        builder.redirectErrorStream(true);
        Process process = builder.start();
        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
        String ln = null;
        while ((ln = reader.readLine()) != null) {
           sb.append(ln).append("\n");
        }

        return sb.toString();
    }
}