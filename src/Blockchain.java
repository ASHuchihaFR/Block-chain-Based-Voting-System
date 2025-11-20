import java.util.*;

public class Blockchain {
    public final List<Block> chain = new ArrayList<>();
    public final List<Vote> pendingVotes = new ArrayList<>();
    public final Map<String, Voter> voterDatabase = new HashMap<>();
    public final Set<String> votedUsers = new HashSet<>();

    public final String[] candidates;
    public final int[] voteCount;

    public Blockchain(String[] candidates) {
        this.candidates = candidates;
        this.voteCount = new int[candidates.length];
        // Genesis block
        chain.add(new Block(0, System.currentTimeMillis(), Collections.emptyList(), "0"));
    }

    public boolean registerVoter(String voterID, String password, String email) {
        if (voterDatabase.containsKey(voterID) || voterID == null || voterID.isEmpty() || password == null || password.isEmpty()) {
            return false;
        }
        voterDatabase.put(voterID, new Voter(voterID, password, email));
        return true;
    }

    public boolean authenticate(String voterID, String password) {
        Voter v = voterDatabase.get(voterID);
        return v != null && v.password.equals(password);
    }

    public synchronized boolean castVote(String voterID, int candidateIndex) {
        if (!voterDatabase.containsKey(voterID) || votedUsers.contains(voterID)) return false;
        if (candidateIndex < 0 || candidateIndex >= candidates.length) return false;

        Vote newVote = new Vote(voterID, candidates[candidateIndex], new Date().toString());
        pendingVotes.add(newVote);
        voteCount[candidateIndex]++;
        votedUsers.add(voterID);

        // Mine a block every 5 votes for demo purposes
        if (pendingVotes.size() >= 5) {
            mineBlock();
        }
        return true;
    }

    public synchronized void mineBlock() {
        if (pendingVotes.isEmpty()) return;
        int newIndex = chain.size();
        String prevHash = chain.get(chain.size() - 1).hash;
        Block newBlock = new Block(newIndex, System.currentTimeMillis(), pendingVotes, prevHash);
        chain.add(newBlock);
        pendingVotes.clear();
    }

    public String blockchainAsText() {
        StringBuilder sb = new StringBuilder();
        for (Block b : chain) {
            sb.append(b.toString()).append("\n");
        }
        return sb.toString();
    }
}