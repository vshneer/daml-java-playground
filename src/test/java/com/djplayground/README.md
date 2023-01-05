### How to use Navigator with tests

1. Add breakpoint BEFORE and AFTER contract creation. For example CreateProposalTest. Add breakpoints to the lines:
    ```
    damlClient.createProposalContract(proposal);
    lookUpProposalContract();
    ```
2. Run IT test in debug mode. Use IntelliJ IDEA debug facilities
3. When execution stops on the breakpoint run a navigator:
    ```
    daml navigator server localhost 6865 --feature-user-management=false
    ```
4. Open navigator in the web browser and log-in on behalf proposer
5. Active contracts list should be empty
6. Now in the debugger press "Resume Program" and debugger will stop on the next breakpoint
7. Check Navigator browser window. You see Proposer contract here. That's it
8. Press "Resume Program" to finish the test
9. Exit Navigator process in the terminal window pressing Command + C

#### See all java processes running
```
watch "sudo lsof -n -i -P | grep LISTEN |grep java"
```