package ex05;

import java.util.Scanner;
import java.util.UUID;

public class Menu {
    private final int ADD_USER = 1;
    private final int VIEW_BALANCE = 2;
    private final int PERFORM_TRANSFER = 3;
    private final int VIEW_TRANSACTIONS = 4;
    private final int REMOVE_TRANSACTION = 5;
    private final int CHECK_TRANSFER = 6;
    private final int EXIT = 7;

    private final int NONE = -1;

    private final String DEV_MODE = "--profile=dev";
    private final String PROD_MODE = "--profile=production";
    private final AppMode mode;
    private final TransactionsService service;

    public Menu(String[] args) {
        if (args.length != 1) {
            throw new IllegalLaunchModeException("Incorrect launch mode");
        } else if (args[0].equals(DEV_MODE)) {
            mode = AppMode.DEV;
        } else if (args[0].equals(PROD_MODE)) {
            mode = AppMode.PRODUCTION;
        } else {
            throw new IllegalLaunchModeException("Incorrect launch mode");
        }
        service = new TransactionsService();
    }

    public void displayMenu() {
        System.out.println("1. Add a user");
        System.out.println("2. View user balances");
        System.out.println("3. Perform a transfer");
        System.out.println("4. View all transactions for a specific user");
        System.out.println("5. DEV – remove a transfer by ID");
        System.out.println("6. DEV – check transfer validity");
        System.out.println("7. Finish execution");
    }

    public void startEventLoop() {
        int choice = 0;
        Scanner inp = new Scanner(System.in);
        while (true) {
            displayMenu();
            choice = scanCommand(inp);
            if (choice == ADD_USER) {
                System.out.println("Enter a user name and a balance:");
                addUser(inp.nextLine());
            } else if (choice == VIEW_BALANCE) {
                viewUserBalance(scanUserId(inp));
            } else if (choice == PERFORM_TRANSFER) {
                System.out.println("Enter a sender ID, a recipient ID, and a transfer amount");
                performTransfer(inp.nextLine());
            } else if (choice == VIEW_TRANSACTIONS) {
                viewTransactions(scanUserId(inp));
            } else if (choice == REMOVE_TRANSACTION) {
                removeTransaction(inp);
            } else if (choice == CHECK_TRANSFER) {
                checkTransfer();
            } else if (choice == EXIT) {
                finishExecution(inp);
                break;
            } else {
                System.out.println("Wrong menu command!");
            }
            System.out.println("---------------------------------------------------------");
        }
    }

    private int scanCommand(Scanner inp) {
        int choice = NONE;
        if (inp.hasNextInt()) {
            choice = inp.nextInt();
        }
        inp.nextLine();
        return choice;
    }

    private int scanUserId(Scanner inp) {
        int id = 0;
        while (true) {
            System.out.println("Enter a user ID");
            if (inp.hasNextInt()) {
                id = inp.nextInt();
                break;
            } else {
                System.out.println("Incorrect data. Please, try again");
                inp.nextLine();
            }
        }
        return id;
    }

    private void addUser(String info) {
        String[] data = info.split(" ");
        if (data.length != 2) {
            System.out.println("Wrong input data");
            return;
        }
        try {
            service.addUser(data[0], Integer.parseInt(data[1]));
            int id = service.getUserId(service.getUsersCount() - 1);
            System.out.println("User with id = " + id + " is added");
        } catch (NumberFormatException e) {
            System.out.println("Wrong balance format!");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    private void viewUserBalance(int id) {
        try {
            System.out.println(service.getUserName(id) + " - " + service.getUserBalance(id));
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void performTransfer(String line) {
        String[] data = line.split(" ");
        if (data.length != 3) {
            System.out.println("Wrong input data");
            return;
        }
        try {
            service.performTransaction(Integer.parseInt(data[0]),
                    Integer.parseInt(data[1]), Integer.parseInt(data[2]));
            System.out.println("The transfer is completed");
        } catch (NumberFormatException e) {
            System.out.println("Wrong input data");
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IllegalTransactionException e) {
            System.out.println("Balance cannot be negative");
        }
    }

    private void viewTransactions(int userId) {
        try {
            Transaction[] transactions = service.getUserTransactions(userId);
            if (transactions.length == 0) {
                System.out.println("No transactions found for user " + service.getUserName(userId));
            }
            for (Transaction t : transactions) {
                User tmp = t.getRecipient();
                String transferType = "To";
                if (t.getCategory() == Transaction.TransferCategory.CREDITS) {
                    transferType = "From";
                }
                System.out.printf("%s %s(id = %d) %d with id = %s\n", transferType, tmp.getName(), tmp.getId(),
                        -t.getTransferAmount(), t.getId());
            }
        } catch (UserNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    private void finishExecution(Scanner inp) {
        System.out.println("Goodbye!");
        inp.close();
    }

    private void removeTransaction(Scanner inp) {
        if (mode == AppMode.DEV) {
            System.out.println("Enter a user ID and a transfer ID");
            String[] data = inp.nextLine().split(" ");
            if (data.length != 2) {
                System.out.println("Wrong input");
                return;
            }
            try {
                int userId = Integer.parseInt(data[0]);
                Transaction removed = service.removeUserTransaction(userId,
                        UUID.fromString(data[1]));
                printRemovedTransaction(removed);
            } catch (NumberFormatException e) {
                System.out.println("Wrong user ID data");
            } catch (IllegalArgumentException e) {
                System.out.println("Wrong transaction ID format");
            } catch (UserNotFoundException | TransactionNotFoundException e) {
                System.out.println(e.getMessage());
            }
        } else {
            System.out.println("Permission denied!");
        }
    }

    private void checkTransfer() {
        if (mode == AppMode.DEV) {
            System.out.println("Check results:");
            Transaction[] lostTransactions =
                    service.checkValidityOfTransactions();
            if (lostTransactions.length == 0) {
                System.out.println("All transactions correct!");
            } else {
                for (Transaction t : lostTransactions) {
                    User sender = t.getSender();
                    User recipient = t.getRecipient();
                    String transferType = "from";
                    if (t.getCategory() == Transaction.TransferCategory.DEBITS) {
                        transferType = "to";
                    }
                    System.out.printf("%s(id = %d) has an unacknowledged transfer id = %s " +
                                    "%s %s(id = %d) for %d\n", sender.getName(), sender.getId(),
                            t.getId(), transferType, recipient.getName(),
                            recipient.getId(), -t.getTransferAmount());
                }
            }
        } else {
            System.out.println("Permission denied!");
        }
    }

    private void printRemovedTransaction(Transaction transaction) {
        String transferType = "To";
        if (transaction.getCategory() == Transaction.TransferCategory.CREDITS) {
            transferType = "From";
        }
        User recipient = transaction.getRecipient();
        System.out.printf("Transfer %s %s(id = %d) %d removed\n", transferType,
                recipient.getName(), recipient.getId(), transaction.getTransferAmount());
    }

    private enum AppMode {
        DEV,
        PRODUCTION
    }
}
