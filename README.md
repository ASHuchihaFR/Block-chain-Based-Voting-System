# Blockchain Based Voting System (Java Swing)

This is a desktop demo of a blockchain-inspired voting system matching your previous project. It includes:
- Voter registration & authentication (HashMap)
- One-person-one-vote enforcement
- Vote recording to a simple blockchain (blocks mined every 5 votes)
- Admin dashboard with a pie chart and full blockchain log
- Java Swing GUI and beep feedback

## Candidates
Default candidates: Alice, Bob, Charlie, Diana (edit in `VotingSystemGUI` if needed).

## How to Compile & Run

### Using `javac`/`java`
```bash
cd src
javac *.java
java VotingSystemGUI
```

### Using VS Code (Java Extension) or IntelliJ
Create a Project, add these sources under `src/`, and run `VotingSystemGUI` or `Main`.

## Admin Credentials
- Username: `admin`
- Password: `admin123`

## Notes
- In-memory data (no DB). Data resets on exit, as per the original description.
- For demo purposes, a block is mined every 5 votes; click "Refresh & Mine Pending" in Admin to force mining.
- This code mirrors the structure and features outlined in your report and synopsis.