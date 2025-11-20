public class Vote {
    public final String voterId;
    public final String candidate;
    public final String timestamp;

    public Vote(String voterId, String candidate, String timestamp) {
        this.voterId = voterId;
        this.candidate = candidate;
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "Vote{voterId='" + voterId + "', candidate='" + candidate + "', timestamp='" + timestamp + "'}";
    }
}