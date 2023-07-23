import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FileManager {

    /**
     * Loads file from given Path, and creates an EnrollmentProfile object from each line.
     * Checks for duplicate profileIds, and overwrites if the new profile's version number is higher.
     * 
     * @param csvPath {@link Path} to the file to be parsed.
     * @return {@link Map} of {@link EnrollmentProfile} objects, keyed on their profileId value.
     * @throws IOException if something goes wrong parsing the file.
     * @throws Exception if there is bad data in the file (duplicate profileIDs with the same version, but different values).
     */
    protected static Map<String, EnrollmentProfile> parseFile(final Path csvPath) throws IOException, Exception {
    Map<String, EnrollmentProfile> profiles = new HashMap<>();

    List<String> lines = Files.readAllLines(csvPath);

    for(String line : lines.subList(1, lines.size())) {
        List<String> values = Arrays.asList(line.split(","));

        //Create our EnrollmentProfile object, ignoring the header line
        EnrollmentProfile profile = new EnrollmentProfile.Builder()
                            .setId(values.get(0))
                            .setFirstName(values.get(1))
                            .setLastName(values.get(2))
                            .setVersion(Integer.parseInt(values.get(3)))
                            .setInsuranceCompany(values.get(4))
                            .build();

        //Check for duplicates, replace if version number is higher
        if(profiles.containsKey(profile.getProfileId())) {
            final EnrollmentProfile existingProfile = profiles.get(profile.getProfileId());

            if((existingProfile.getVersion() < profile.getVersion())) {
                profiles.replace(profile.getProfileId(), profile);
            }
            else if((existingProfile.getVersion() == profile.getVersion()) && !existingProfile.equals(profile)) {
                throw new Exception("There is bad data in the file, multiple entries with matching profileID and Version.");
            }
        }
        else {
            //No duplicate, just add it to the Map
            profiles.put(profile.getProfileId(), profile);
        }
    }

    return profiles;
    }

    /**
     * Writes each group of Enrollment Ids to a file named after their insurance company.
     * 
     * @param groupMap {@link Map} of {@link List} containing {@link EnrollmentProfile} values, keyed on their shared insurance company name value.
     * @return String list of file names of created .csv files.
     */
    protected static List<String> writeToFile(final String fileName, final Map<String, List<EnrollmentProfile>> groupMap) throws Exception{
        List<String> filesCreated = new ArrayList<>();

        for(String groupName : groupMap.keySet()) {

            final List<EnrollmentProfile> profiles = groupMap.get(groupName);

            final File outputFile = new File("src\\Assignment2\\Output", groupName + ".csv");

            try(PrintWriter writer = new PrintWriter(outputFile)) {
                //Add the header line
                writer.println("UserID,FirstName,LastName,Version,InsuranceCompany");

                //Add the values
                for(EnrollmentProfile profile : profiles) {
                    writer.println(profile.getAsString());
                }
            }
            catch(IOException e) {
                throw new Exception("IOException: Error writing to file: " + outputFile.getName(), e);
            }

            filesCreated.add(groupName + ".csv");
        }

        return filesCreated;
    }
    }
