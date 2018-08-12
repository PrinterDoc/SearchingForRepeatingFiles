import java.io.*;
import java.util.*;

public class SearchingForRepeatingFiles {

    public static List<File> getFiles(String pathName) {

        File directoryName = new File(pathName);
        int i = 0;
        List<File> returnList = new ArrayList<>();
        if (directoryName.isDirectory()) {
            if (Objects.requireNonNull(directoryName.listFiles()).length != 0) {
                for (File item : Objects.requireNonNull(directoryName.listFiles())) {
                    if (item.isFile()) {
                        returnList.add(i,item);
                        i += 1;
                    } else System.out.println(item.getName() + " не является файлом");
                }
            } else System.out.println("Папка пуста");
        } else System.out.println("Папка не существует");
        return returnList;
    }

    public static TreeMap<String,HashMap<String,Integer>> repeatCountMap = new TreeMap<>();

    public static void searchParts(File file) {

        try (FileInputStream fis = new FileInputStream(file)) {
           final char SPACE = ' ';
            int read;
            StringBuilder wordBuff = new StringBuilder();
            while ((read = fis.read()) > 0) {
                if ((char) read != SPACE) {
                    wordBuff.append((char) read);
                }
                if ((char) read == SPACE) {
                    String word = wordBuff.toString();
                    wordBuff.delete(0, wordBuff.length());
                    if (!repeatCountMap.containsKey(word)) {
                        repeatCountMap.put(word, new HashMap<>());
                        repeatCountMap.get(word).put(file.getName(), 1);
                    } else {
                        if (!repeatCountMap.get(word).containsKey(file.getName())) {
                            repeatCountMap.get(word).put(file.getName(), 1);
                        } else {
                            repeatCountMap.get(word).put(file.getName(), repeatCountMap.get(word).get(file.getName()) + 1);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public static void main(String[] args) {

        List<File> fileList = getFiles("C:\\Users\\PC\\Desktop\\SomeDir");

        for(File file: fileList) { searchParts(file); }

        for(String word: repeatCountMap.keySet()){
            HashMap<String, Integer> descPartMap = repeatCountMap.get(word);
            HashMap<Integer, ArrayList<String>> result = new HashMap<>();
            //  поиск файлов с одинаковым количеством вхождений слова
            for (String key : descPartMap.keySet()) {
                ArrayList<String> equalFile;
                if(descPartMap.get(key) >1) {
                    if (!result.containsKey(descPartMap.get(key))) {
                        equalFile = new ArrayList<>();
                        equalFile.add(key);
                        result.put(descPartMap.get(key), equalFile);
                    } else {
                        equalFile = result.get(descPartMap.get(key));
                        equalFile.add(key);
                        result.put(descPartMap.get(key), equalFile);
                    }
                }
            }
                //  вывод списка хотя бы 2х файлов с одинаковым количеством повторений
                for (Integer key : result.keySet()) {
                if(result.get(key).size() >1 )
                    {
                        System.out.print("Повторяющаяся часть: "+word+" Количество повторений: "+ key + " Файлы: " + result.get(key) + "\n");
                    }
                }
        }

    }
}