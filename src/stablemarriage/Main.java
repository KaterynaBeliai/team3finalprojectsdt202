package jobmatching;

import stablemarriage.Candidate;
import stablemarriage.Company;
import stablemarriage.GaleShapley;
import stablemarriage.Matching;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        while (true) {

            printMenu();

            String choice = scanner.nextLine().trim();

            if (choice.equals("1")) {

                runExample();

            } else if (choice.equals("2")) {

                runManualInput();

            } else if (choice.equals("3")) {

                System.out.println("Goodbye!");
                break;

            } else {

                System.out.println(
                        "Invalid option. Please choose 1, 2, or 3."
                );
            }
        }

        scanner.close();
    }

    private static void printMenu() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          JOB MATCHING SYSTEM");
        System.out.println("       Gale-Shapley Algorithm");
        System.out.println("========================================");
        System.out.println("1. Use example preferences");
        System.out.println("2. Enter preferences manually");
        System.out.println("3. Exit");
        System.out.println("========================================");
        System.out.print("Choose an option: ");
    }

    /*
     * Runs a predefined example.
     * This is useful for demonstrating the algorithm
     * quickly during a presentation.
     */
    private static void runExample() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          EXAMPLE DATA");
        System.out.println("========================================");

        Candidate anna = new Candidate(
                "Anna",
                List.of(
                        "Google",
                        "Microsoft",
                        "Amazon"
                )
        );

        Candidate alex = new Candidate(
                "Alex",
                List.of(
                        "Amazon",
                        "Google",
                        "Microsoft"
                )
        );

        Candidate sofia = new Candidate(
                "Sofia",
                List.of(
                        "Microsoft",
                        "Amazon",
                        "Google"
                )
        );

        Company google = new Company(
                "Google",
                List.of(
                        "Alex",
                        "Anna",
                        "Sofia"
                )
        );

        Company microsoft = new Company(
                "Microsoft",
                List.of(
                        "Sofia",
                        "Anna",
                        "Alex"
                )
        );

        Company amazon = new Company(
                "Amazon",
                List.of(
                        "Anna",
                        "Sofia",
                        "Alex"
                )
        );

        List<Candidate> candidates = List.of(
                anna,
                alex,
                sofia
        );

        List<Company> companies = List.of(
                google,
                microsoft,
                amazon
        );

        printPreferences(candidates, companies);

        runAlgorithm(candidates, companies);
    }

    /*
     * Allows the user to create their own
     * candidates, companies, and preferences.
     */
    private static void runManualInput() {

        System.out.println();
        System.out.println("========================================");
        System.out.println("          MANUAL INPUT");
        System.out.println("========================================");

        int numberOfPeople = readPositiveInteger(
                "Enter number of candidates/companies: "
        );

        List<String> candidateNames =
                readNames(
                        numberOfPeople,
                        "candidate"
                );

        List<String> companyNames =
                readNames(
                        numberOfPeople,
                        "company"
                );

        List<Candidate> candidates =
                createCandidates(
                        candidateNames,
                        companyNames
                );

        List<Company> companies =
                createCompanies(
                        companyNames,
                        candidateNames
                );

        printPreferences(candidates, companies);

        runAlgorithm(candidates, companies);
    }

    /*
     * Creates candidates and asks the user
     * to enter their company preferences.
     */
    private static List<Candidate> createCandidates(
            List<String> candidateNames,
            List<String> companyNames) {

        List<Candidate> candidates =
                new ArrayList<>();

        for (String candidateName : candidateNames) {

            System.out.println();
            System.out.println(
                    "Enter preferences for "
                            + candidateName
            );

            List<String> preferences =
                    readPreferenceOrder(
                            companyNames,
                            "company"
                    );

            candidates.add(
                    new Candidate(
                            candidateName,
                            preferences
                    )
            );
        }

        return candidates;
    }

    /*
     * Creates companies and asks the user
     * to enter their candidate preferences.
     */
    private static List<Company> createCompanies(
            List<String> companyNames,
            List<String> candidateNames) {

        List<Company> companies =
                new ArrayList<>();

        for (String companyName : companyNames) {

            System.out.println();
            System.out.println(
                    "Enter preferences for "
                            + companyName
            );

            List<String> preferences =
                    readPreferenceOrder(
                            candidateNames,
                            "candidate"
                    );

            companies.add(
                    new Company(
                            companyName,
                            preferences
                    )
            );
        }

        return companies;
    }

    /*
     * Reads a complete preference order.
     *
     * Example:
     *
     * Available companies:
     * 1. Google
     * 2. Microsoft
     * 3. Amazon
     *
     * Enter preference 1: Google
     * Enter preference 2: Amazon
     * Enter preference 3: Microsoft
     */
    private static List<String> readPreferenceOrder(
            List<String> availableOptions,
            String optionType) {

        List<String> preferences =
                new ArrayList<>();

        System.out.println();
        System.out.println(
                "Available " + optionType + "s:"
        );

        for (int i = 0; i < availableOptions.size(); i++) {

            System.out.println(
                    (i + 1)
                            + ". "
                            + availableOptions.get(i)
            );
        }

        System.out.println();
        System.out.println(
                "Enter preferences from most preferred "
                        + "to least preferred."
        );

        for (int i = 0; i < availableOptions.size(); i++) {

            while (true) {

                System.out.print(
                        "Preference "
                                + (i + 1)
                                + ": "
                );

                String input =
                        scanner.nextLine().trim();

                if (!availableOptions.contains(input)) {

                    System.out.println(
                            "Invalid choice. "
                                    + "Please enter one of the "
                                    + "available names."
                    );

                    continue;
                }

                if (preferences.contains(input)) {

                    System.out.println(
                            "You already selected "
                                    + input
                                    + ". Choose another option."
                    );

                    continue;
                }

                preferences.add(input);
                break;
            }
        }

        return preferences;
    }

    /*
     * Prints all preferences before running
     * the Gale-Shapley algorithm.
     */
    private static void printPreferences(
            List<Candidate> candidates,
            List<Company> companies) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("             PREFERENCES");
        System.out.println("========================================");

        System.out.println();
        System.out.println("Candidate preferences:");

        for (Candidate candidate : candidates) {

            System.out.println(
                    "  "
                            + candidate.getName()
                            + ": "
                            + String.join(
                            " > ",
                            candidate.getCompanyPreferences()
                    )
            );
        }

        System.out.println();
        System.out.println("Company preferences:");

        for (Company company : companies) {

            System.out.println(
                    "  "
                            + company.getName()
                            + ": "
                            + String.join(
                            " > ",
                            company.getCandidatePreferences()
                    )
            );
        }
    }

    /*
     * Runs the Gale-Shapley algorithm
     * and prints the final matching.
     */
    private static void runAlgorithm(
            List<Candidate> candidates,
            List<Company> companies) {

        System.out.println();
        System.out.println("========================================");
        System.out.println("       RUNNING GALE-SHAPLEY");
        System.out.println("========================================");
        System.out.println();

        GaleShapley algorithm =
                new GaleShapley();

        Matching result =
                algorithm.findStableMatching(
                        candidates,
                        companies
                );

        result.printResults();

        System.out.println();
        System.out.println(
                "The matching has been completed."
        );
    }

    /*
     * Reads a positive integer from the user.
     */
    private static int readPositiveInteger(
            String message) {

        while (true) {

            System.out.print(message);

            String input =
                    scanner.nextLine().trim();

            try {

                int number =
                        Integer.parseInt(input);

                if (number > 0) {
                    return number;
                }

                System.out.println(
                        "Please enter a number greater than 0."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Invalid input. Please enter a number."
                );
            }
        }
    }

    /*
     * Reads unique names from the user.
     */
    private static List<String> readNames(
            int count,
            String type) {

        List<String> names =
                new ArrayList<>();

        System.out.println();

        for (int i = 0; i < count; i++) {

            while (true) {

                System.out.print(
                        "Enter "
                                + type
                                + " "
                                + (i + 1)
                                + " name: "
                );

                String name =
                        scanner.nextLine().trim();

                if (name.isEmpty()) {

                    System.out.println(
                            "Name cannot be empty."
                    );

                    continue;
                }

                if (names.contains(name)) {

                    System.out.println(
                            "This name already exists. "
                                    + "Please enter a different name."
                    );

                    continue;
                }

                names.add(name);
                break;
            }
        }

        return names;
    }
}