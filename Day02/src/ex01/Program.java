package ex01;

public class Program {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Not enough arguments!");
            System.exit(-1);
        }
        WordsDictionary dictionary = DictionaryCreator.getInstance()
                .createDictionary(args[0], args[1]);
        DictionaryWriter writer = new DictionaryWriter();
        writer.writeDictionaryToFile(dictionary);
        CosineMeasureCalculator calculator = new CosineMeasureCalculator();
        System.out.printf("Similarity = %.3f", calculator.calculateSimilarity(dictionary));
    }
}