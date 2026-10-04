package stablemarriage;

import java.util.LinkedHashMap;
import java.util.Map;

public class Matching {

    private final Map<String, String> matches;

    public Matching() {
        matches = new LinkedHashMap<>();
    }

    public void addMatch(String candidate, String company) {
        if (candidate == null || company == null) {
            throw new IllegalArgumentException(
                    "Candidate and company cannot be null."
            );
        }

        matches.put(candidate, company);
    }

    public String getCompanyFor(String candidate) {
        return matches.get(candidate);
    }

    public int size() {
        return matches.size();
    }

    public void printResults() {
        System.out.println();
        System.out.println("========================================");
        System.out.println("           FINAL MATCHING");
        System.out.println("========================================");

        for (Map.Entry<String, String> entry : matches.entrySet()) {
            System.out.printf(
                    "%-15s -> %s%n",
                    entry.getKey(),
                    entry.getValue()
            );
        }

        System.out.println("========================================");
        System.out.println("Total matches: " + matches.size());
    }
}