# Synopsis — Blockchain Based Voting System

**Abstract:** Traditional voting systems face fraud, opacity, and inefficiency. This system uses an immutable ledger and basic cryptography to record votes securely, aiming for transparency and tamper-resistance while keeping voters authenticated.

**Problem Statement:** Paper/EVM/centralized e-voting suffer tampering, weak auditability, and single points of failure. Need a decentralized, verifiable design that preserves privacy and integrity.

**Algorithm (short):** On registration, `(voterId -> password,email)` stored in a `HashMap`. On login, credentials checked. A successful vote creates a `Vote(voterId,candidate,timestamp)` appended to a `pendingVotes (ArrayList)`. Every 5 votes, a `Block(index,timestamp,votes,prevHash)` is mined with SHA‑256 over its contents and linked via `previousHash`. An `int[] voteCount` is maintained; a `LinkedList` could be used for chronological history but the chain itself preserves order.

**Data Structures:** `ArrayList<Vote>` for pending and block contents; `HashMap<String,Voter>` for auth; `int[]` for tallies; `Set<String>` to stop double voting; `List<Block>` chain for immutability.

**SWOT (short):**  
- Strengths: transparency, integrity, low paper cost, privacy.  
- Weaknesses: scalability, endpoint security, digital literacy.  
- Opportunities: remote voting, corporate governance, DAO polls.  
- Threats: regulation gaps, cyber risks, public skepticism.

**Applications:** Government & local elections (pilot scale), shareholder/club elections, university polls, referendums, online surveys with auditable results.

**References (sample):** S. Nakamoto (2008), Oracle Java Docs, IBM Blockchain voting explainer, survey papers on blockchain voting.