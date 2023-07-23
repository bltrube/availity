package Assignment2;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Main class for this assignment.
 */
public class CSVSorter {
        public static void main(String[] args) throws Exception {
            System.out.println("Availity Homework Assignment 2: CSV Sorter");
            System.out.println("Loading files from src\\Assignment2\\Input");

            //Get our files to be parsed
            final Set<Path> filePaths = Files.list(Paths.get("src\\Assignment2\\Input"))
                                    .collect(Collectors.toSet());

            for(Path csvPath : filePaths) {
                try {
                    //Read csv, and get the enrollment profiles
                    Map<String, EnrollmentProfile> profilesMap = FileManager.parseFile(csvPath);

                    //Group the profiles by insuranceCompany, and alphabetize them
                    Map<String, List<EnrollmentProfile>> sortedProfilesMap = sortByCompany(profilesMap);

                    //Write each group of profiles to their respective files
                    List<String> result = FileManager.writeToFile(csvPath.getFileName().toString(), sortedProfilesMap);

                    //Check to see if we created any files and complete
                    if(result.size() > 0) {
                        System.out.println("Success!");
                        System.out.println("Created Files: " + result.toString());
                        System.out.println("Generated files can be found in: src\\Assignment2\\Output");
                    }
                    else {
                        //Something went wrong, no files were created
                        System.out.println("Error");
                        System.exit(1);
                    }
                
                }
                catch(Exception e) {
                    System.out.println("General exception: " + e.getMessage());
                    System.exit(-1);
                }
            }
        }

        /**
         * Utilizes Collectors.groupingBy() to divide the EnrollmentProfiles up by insurance company.
         * Then streams through each group and alphabetizes the entries by lastName, then firstName;
         * 
         * @param profiles {@link Map} with {@link EnrollmentProfile} values, keyed on each profile's profileId.
         * @return Map with {@link List} values containing EnrollmentProfiles, keyed on the profiles' shared insuranceCompany values.
         */
        private static Map<String, List<EnrollmentProfile>> sortByCompany(Map<String, EnrollmentProfile> profiles) {

            //Group by insurance company
            Map<String, List<EnrollmentProfile>> sorted = profiles.values().stream()
                                            .collect(Collectors.groupingBy(EnrollmentProfile::getInsuranceCompany));

            //Order each list alphabetically by Last, First
            for(String x : sorted.keySet()) {
                List<EnrollmentProfile> profileList = sorted.get(x);

                List<EnrollmentProfile> sortedList = profileList.stream()
                                                .sorted(Comparator.comparing(EnrollmentProfile::getLastName)
                                                    .thenComparing(EnrollmentProfile::getFirstName))
                                                .collect(Collectors.toList());

                sorted.replace(x, sortedList);
            }

            return sorted;
        }
}
