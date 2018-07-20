# The Vertex Network #
By Khedron

This was my final project for my CS 201 class, freshman year at Lewis and Clark College. My main objectives were:


1.  Implementing a proof-of-work mining system, using hashes and nonces.
2.  Creating blocks to hold transaction information, secured by that POW mining system. Each block contains the hash of the previous block, thus "chaining" the blocks together. Modifying a past block would invalidate the hash in the next, and therefore invalidate the validity of the chain. This is the core of the project, a blockchain. 
3.  Adding a mining function and reward to incentivize network participation, in the form of users mining blocks.
4.  Create multiple accounts that can hold a balance of coins. Implement methods that can parse the blockchain to update account balances and other information.

There is no networked component to this blockchain. There are pregenerated accounts that can interact with each other by sending and receiving transactions, and the user can switch between these accounts. There are a few other modifications/simplifications made to The Vertex Network that separate it from a real cryptocurrency system, such as the mining reward coming from some kind of mysterious superuser called Odin instead of generating new coins in every block.

The reason for these simplifications is that I had four months of java experience, a two week deadline, and cryptocurrency is complicated. 

**Updates that would enhance this project:**

- Networking the program, so that blocks are always being mined and the program can be downloaded and run by multiple parties. This would be the most complex update to implement, as it would also introduce a need for consensus algorithms implemented in almost every step of the program to keep the network decentralized. 
- Allow users the choice to run a full node or simply to send transactions out to the network to be mined by others. 
- Change the mining reward system to generate new coins, instead of giving them to the miner from the locked Odin account.
- A dynamic difficulty adjustment feature, that would change the expected block time as the number of miners on the network changes. 
- Allow users to create new accounts, with usernames and passwords. Store login information as hashes in blocks, so that these creds are kept distributed and decentralized. Let users sign in and sign out as different accounts. 
- A simple method that will check the hashes in each block to regularly ensure that no data has been tampered with. 

Credit goes to Prof. Noshad for a great course, and my TA and good friend Ahmed for his Dedication, Respect, Understanding, Nurturing Kindness, Encouragement, Niceness, Responsiveness, Attitude, Mentorship, Belief, Love, Idealism, Nobility and Good Spirits.  