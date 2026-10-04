package stablemarriage;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GaleShapley {

    public Matching findStableMatching(
            List<Candidate> candidates,
            List<Company> companies) {

        validateInput(candidates, companies);

        Map<String, Company> companyMap =
                createCompanyMap(companies);

        Deque<Candidate> freeCandidates =
                new ArrayDeque<>(candidates);

        while (!freeCandidates.isEmpty()) {

            Candidate candidate =
                    freeCandidates.removeFirst();

            if (!candidate.hasMorePreferences()) {
                continue;
            }

            String companyName =
                    candidate.getNextCompany();

            Company company =
                    companyMap.get(companyName);

            if (company == null) {
                throw new IllegalArgumentException(
                        "Company '" + companyName + "' does not exist."
                );
            }

            System.out.println(
                    candidate.getName()
                            + " proposes to "
                            + company.getName()
            );

            if (company.isFree()) {

                company.acceptCandidate(
                        candidate.getName()
                );

                System.out.println(
                        company.getName()
                                + " accepts "
                                + candidate.getName()
                );

            } else {

                String currentCandidate =
                        company.getCurrentCandidate();

                if (company.prefers(
                        candidate.getName(),
                        currentCandidate)) {

                    company.acceptCandidate(
                            candidate.getName()
                    );

                    System.out.println(
                            company.getName()
                                    + " prefers "
                                    + candidate.getName()
                                    + " over "
                                    + currentCandidate
                    );

                    System.out.println(
                            currentCandidate
                                    + " becomes free"
                    );

                    Candidate rejectedCandidate =
                            findCandidate(
                                    candidates,
                                    currentCandidate
                            );

                    freeCandidates.addLast(
                            rejectedCandidate
                    );

                } else {

                    System.out.println(
                            company.getName()
                                    + " rejects "
                                    + candidate.getName()
                    );

                    freeCandidates.addLast(candidate);
                }
            }

            System.out.println();
        }

        return createMatching(companies);
    }

    private Map<String, Company> createCompanyMap(
            List<Company> companies) {

        Map<String, Company> companyMap =
                new HashMap<>();

        for (Company company : companies) {
            companyMap.put(
                    company.getName(),
                    company
            );
        }

        return companyMap;
    }

    private Candidate findCandidate(
            List<Candidate> candidates,
            String name) {

        for (Candidate candidate : candidates) {

            if (candidate.getName().equals(name)) {
                return candidate;
            }
        }

        throw new IllegalArgumentException(
                "Candidate '" + name + "' does not exist."
        );
    }

    private Matching createMatching(
            List<Company> companies) {

        Matching matching = new Matching();

        for (Company company : companies) {

            String candidate =
                    company.getCurrentCandidate();

            if (candidate != null) {
                matching.addMatch(
                        candidate,
                        company.getName()
                );
            }
        }

        return matching;
    }

    private void validateInput(
            List<Candidate> candidates,
            List<Company> companies) {

        if (candidates == null || companies == null) {
            throw new IllegalArgumentException(
                    "Candidates and companies cannot be null."
            );
        }

        if (candidates.isEmpty()) {
            throw new IllegalArgumentException(
                    "There must be at least one candidate."
            );
        }

        if (companies.isEmpty()) {
            throw new IllegalArgumentException(
                    "There must be at least one company."
            );
        }

        if (candidates.size() != companies.size()) {
            throw new IllegalArgumentException(
                    "The number of candidates and companies must be equal."
            );
        }
    }
}