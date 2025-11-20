import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {
    public final int index;
    public final long timestamp;
    public final List<Vote> votes;
    public final String previousHash;
    public final String hash;

    public Block(int index, long timestamp, List<Vote> votes, String previousHash) {
        this.index = index;
        this.timestamp = timestamp;
        this.votes = new ArrayList<>(votes);
        this.previousHash = previousHash;
        this.hash = calculateHash();
    }

    private String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }

    public String calculateHash() {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            String data = index + ":" + timestamp + ":" + votes.toString() + ":" + previousHash;
            byte[] hashBytes = digest.digest(data.getBytes("UTF-8"));
            return bytesToHex(hashBytes);
        } catch (Exception e) {
            throw new RuntimeException("Error calculating hash", e);
        }
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Block #").append(index).append("\n");
        sb.append("Timestamp: ").append(new Date(timestamp)).append("\n");
        sb.append("Previous Hash: ").append(previousHash).append("\n");
        sb.append("Hash: ").append(hash).append("\n");
        sb.append("Votes:\n");
        for (Vote v : votes) {
            sb.append("  - ").append(v).append("\n");
        }
        return sb.toString();
    }
}