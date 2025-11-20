# Minor Project Report — Blockchain Based Voting System

## Certificate / Acknowledgment
(Replace with institute text and signatures.)

## Abstract
Traditional voting systems face challenges like fraud, lack of transparency, and inefficiencies. This project provides a tamper-resistant voting demo using a simple blockchain, recording votes in chained blocks with SHA‑256 hashes, plus a Java Swing GUI and admin tools.

## Introduction
Motivation, goals, and a brief on blockchain, hashing, and immutability.

## Literature Review (short)
Estonia e‑voting background; pilots like Voatz; academic surveys on blockchain voting—benefits and limitations.

## Theoretical Background
- Blockchain structure, hash pointers, immutability
- Public‑key cryptography (conceptual)
- Smart contracts (conceptual reference for future work)

## System Design
- **Modules:** Registration, Authentication & Voting, Admin
- **Architecture:** GUI (Swing), App logic, Blockchain layer, In‑memory storage
- **UML:** Use‑case, activity and class descriptions (diagrams can be added later)

## Technologies Used
Java 8+, Swing, Collections (`HashMap`, `ArrayList`), Graphics for pie chart.

## Implementation (highlights)
- `registerVoter`, `authenticate`, `castVote` (one vote per user)
- Block mined each 5 votes; forced mining from Admin
- Admin pie chart and blockchain text view
- Beep feedback on success

## Security Analysis
- Pros: immutable chain, basic auth, auditability
- Gaps (future work): encrypt ballots, MFA/OTP, secure storage, database, remote/mobile client

## Testing (examples)
- Registration edge cases (duplicate/empty)
- Auth success/failure
- Double‑vote prevented
- Vote counts tally vs. blockchain contents
- GUI usability checks

## Results
Functional demo with transparent counts and verifiable blocks.

## Limitations
Desktop‑only, volatile memory, no encryption/MFA, demo admin credentials.

## Future Enhancements
MFA/OTP, DB & cloud sync, mobile app, biometric verification, export logs, vote encryption/end‑to‑end verifiability.

## Conclusion
A practical demo showing how blockchain principles can improve election transparency and auditability.

## References
- Nakamoto, S. (2008). *Bitcoin: A Peer-to-Peer Electronic Cash System*.
- Oracle Java SE 8 API.
- IBM: How blockchain can be used in voting.
- Survey papers on blockchain voting.