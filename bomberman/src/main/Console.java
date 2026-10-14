package main;

public interface Console {
    static void clearConsole(){
        System.out.println(new String(new char[50]).replace("\0", "\r\n"));
    }
    static void sleep(int miliseconds){
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    static void sleepL(long miliseconds){
        try {
            Thread.sleep(miliseconds);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
}