package stablemarriage;

import java.util.ArrayList;
import java.util.List;

public class Candidate {

    private final String name;
    private final List<String> companyPreferences;
    private int nextPreferenceIndex;

    public Candidate(String name, List<String> companyPreferences) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException(
                    "Candidate name cannot be empty."
            );
        }

        if (companyPreferences == null || companyPreferences.isEmpty()) {
            throw new IllegalArgumentException(
                    "Candidate must have at least one company preference."
            );
        }

        this.name = name;
        this.companyPreferences = new ArrayList<>(companyPreferences);
        this.nextPreferenceIndex = 0;
    }

    public String getName() {
        return name;
    }

    public List<String> getCompanyPreferences() {
        return new ArrayList<>(companyPreferences);
    }

    public boolean hasMorePreferences() {
        return nextPreferenceIndex < companyPreferences.size();
    }

    public String getNextCompany() {
        if (!hasMorePreferences()) {
            return null;
        }

        String company = companyPreferences.get(nextPreferenceIndex);
        nextPreferenceIndex++;

        return company;
    }

    @Override
    public String toString() {
        return name;
    }
}
