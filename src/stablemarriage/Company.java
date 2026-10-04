package stablemarriage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Company {

    private final String name;
    private final Map<String, Integer> candidateRanking;
    private final List<String> candidatePreferences;
    private String currentCandidate;

    public Company(String name, List<String> candidatePreferences) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Company name cannot be empty."
            );
        }

        if (candidatePreferences == null || candidatePreferences.isEmpty()) {
            throw new IllegalArgumentException(
                    "Company must have at least one candidate preference."
            );
        }

        this.name = name;
        this.candidatePreferences =
                new ArrayList<>(candidatePreferences);

        this.candidateRanking = new HashMap<>();
        this.currentCandidate = null;

        for (int i = 0; i < candidatePreferences.size(); i++) {
            candidateRanking.put(
                    candidatePreferences.get(i),
                    i
            );
        }
    }

    public String getName() {
        return name;
    }

    public List<String> getCandidatePreferences() {
        return new ArrayList<>(candidatePreferences);
    }

    public String getCurrentCandidate() {
        return currentCandidate;
    }

    public boolean isFree() {
        return currentCandidate == null;
    }

    public boolean prefers(
            String newCandidate,
            String currentCandidate) {

        Integer newCandidateRank =
                candidateRanking.get(newCandidate);

        Integer currentCandidateRank =
                candidateRanking.get(currentCandidate);

        if (newCandidateRank == null
                || currentCandidateRank == null) {

            throw new IllegalArgumentException(
                    "Candidate is not in the company's preference list."
            );
        }

        return newCandidateRank < currentCandidateRank;
    }

    public void acceptCandidate(String candidate) {
        this.currentCandidate = candidate;
    }

    @Override
    public String toString() {
        return name;
    }
}