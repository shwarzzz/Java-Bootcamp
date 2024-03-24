package ex03;

import java.util.Scanner;

public class Program {
    public static final String THREAD_FLAG = "--threadsCount";

    public static void main(String[] args) {
        if (args.length != 1) {
            ArgumentsValidator.printError("Incorrect arguments count!");
        }
        int threadsCount = ArgumentsValidator.validate(args[0], THREAD_FLAG);
        ThreadsFileLoader loader = new ThreadsFileLoader(threadsCount);
        System.out.println("Enter path to \"files_urls.txt\":");
        Scanner inp = new Scanner(System.in);
        String pathToUrlsFile = inp.nextLine();
        inp.close();
        loader.downloadFiles(pathToUrlsFile);
    }
}
