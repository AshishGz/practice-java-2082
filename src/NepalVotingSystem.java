import java.io.*;
import java.util.*;

class Candidate {
    String name;
    String symbol;
    int votes;

    Candidate(String name, String symbol) {
        this.name = name;
        this.symbol = symbol;
        this.votes = 0;
    }
}

public class NepalVotingSystem {

    static List<Candidate> candidates = new ArrayList<>();
    static Set<String> votedVoters = new HashSet<>();

    static final String candidateFile = "candidates.txt";
    static final String voteFile = "votes.txt";

    public static void main(String[] args) {

        loadCandidates();
        loadPreviousVotes();

        Scanner sc = new Scanner(System.in);

        while (true) {

            System.out.println("\n===== Nepal Voting System =====");
            System.out.println("1. Cast Vote");
            System.out.println("2. Show Results");
            System.out.println("3. Exit");

            System.out.print("Choose option: ");
            int choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {

                case 1:
                    castVote(sc);
                    break;

                case 2:
                    showResults();
                    break;

                case 3:
                    System.out.println("Voting Closed.");
                    return;

                default:
                    System.out.println("Invalid choice");
            }
        }
    }

    // Load candidates from text file
    static void loadCandidates() {

        try {
            BufferedReader br = new BufferedReader(new FileReader(candidateFile));
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                candidates.add(new Candidate(data[0], data[1]));
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading candidates.");
        }
    }

    // Load previous votes
    static void loadPreviousVotes() {

        try {

            File file = new File(voteFile);
            if (!file.exists())
                return;

            BufferedReader br = new BufferedReader(new FileReader(voteFile));
            String line;

            while ((line = br.readLine()) != null) {

                String[] data = line.split(",");
                String voterId = data[0];
                String symbol = data[1];

                votedVoters.add(voterId);

                for (Candidate c : candidates) {
                    if (c.symbol.equals(symbol)) {
                        c.votes++;
                    }
                }
            }

            br.close();

        } catch (Exception e) {
            System.out.println("Error loading votes.");
        }
    }

    static void castVote(Scanner sc) {

        System.out.print("Enter Voter ID: ");
        String voterId = sc.nextLine();

        if (votedVoters.contains(voterId)) {
            System.out.println("You have already voted!");
            return;
        }

        System.out.println("\nCandidates:");

        for (int i = 0; i < candidates.size(); i++) {

            Candidate c = candidates.get(i);

            System.out.println((i + 1) + ". " + c.name + " (" + c.symbol + ")");
        }

        System.out.print("Choose candidate number: ");
        int choice = sc.nextInt();

        if (choice < 1 || choice > candidates.size()) {
            System.out.println("Invalid candidate.");
            return;
        }

        Candidate selected = candidates.get(choice - 1);
        selected.votes++;

        votedVoters.add(voterId);

        saveVote(voterId, selected.symbol);

        System.out.println("Vote Cast Successfully!");
    }

    // Save vote in file
    static void saveVote(String voterId, String symbol) {

        try {

            BufferedWriter bw = new BufferedWriter(new FileWriter(voteFile, true));
            bw.write(voterId + "," + symbol);
            bw.newLine();
            bw.close();

        } catch (Exception e) {
            System.out.println("Error saving vote.");
        }
    }

    static void showResults() {

        System.out.println("\n===== Result =====");

        Candidate winner = candidates.get(0);

        for (Candidate c : candidates) {

            System.out.println(c.name + " (" + c.symbol + ") : " + c.votes + " votes");

            if (c.votes > winner.votes) {
                winner = c;
            }
        }

        System.out.println("\nWinner: " + winner.name + " with " + winner.votes + " votes");
    }
}