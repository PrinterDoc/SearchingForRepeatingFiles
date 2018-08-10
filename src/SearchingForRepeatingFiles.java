import java.io.*;
import java.util.*;

public class SearchingForRepeatingFiles {

    public static List<String> getFilePath(String pathName) {
        /**
         Method return list of paths of files from directory
         *
         Input data: path directory in which searching for files - directoryName
         *
         Return data: List of strings describing paths to files in folder
         */
        File directoryName = new File(pathName);
        int i = 0;
        List<String> returnList = new ArrayList<>();
        if (directoryName.isDirectory()) {
            if (directoryName.listFiles().length != 0) {
                for (File item : directoryName.listFiles()) {
                    if (item.isFile()) {
                        returnList.add(i,item.getPath());
                        i += 1;
                    } else System.out.println(item.getName() + " не является файлом");
                }
            } else System.out.println("Папка пуста");
        } else System.out.println("Папка не существует");
        return returnList;
    }

    public static TreeMap<String,HashMap<String,Integer>> repeatCountMap = new TreeMap<>();//

    public static void searchParts(String filePath) {

        try (FileInputStream fis = new FileInputStream(filePath)) {
           final char SPACE = ' ';
            int read = 0;
            StringBuilder wordBuff = new StringBuilder();// word buff
            while ((read = fis.read()) > 0) {
                if ((char) read != SPACE) {
                    wordBuff.append((char) read);
                }
                if ((char) read == SPACE) {
                    String word = wordBuff.toString();
                    wordBuff.delete(0, wordBuff.length());
                    if (!repeatCountMap.containsKey(word)) {
                        repeatCountMap.put(word, new HashMap<String, Integer>());
                        repeatCountMap.get(word).put(filePath, 1);
                    } else {
                        if (!repeatCountMap.get(word).containsKey(filePath)) {
                            repeatCountMap.get(word).put(filePath, 1);
                        } else {
                            repeatCountMap.get(word).put(filePath, repeatCountMap.get(word).get(filePath) + 1);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {

        List<String> filesPath =getFilePath("C:\\Users\\PC\\Desktop\\SomeDir");

        for(String fPath: filesPath) { searchParts(fPath); }

        for(String word: repeatCountMap.keySet()){ // word
            HashMap<String, Integer> descPartMap = repeatCountMap.get(word);
            HashMap<Integer, ArrayList<String>> result = new HashMap<>();
            for (String key : descPartMap.keySet()) {
                ArrayList<String> equalFile = null;
                if(descPartMap.get(key) >1) {
                    if (!result.containsKey(descPartMap.get(key))) {
                        equalFile = new ArrayList<String>();
                        equalFile.add(key);
                        result.put(descPartMap.get(key), equalFile);
                    } else {
                        equalFile = result.get(descPartMap.get(key));
                        equalFile.add(key);
                        result.put(descPartMap.get(key), equalFile);
                    }
                }
            }
                for (Integer key : result.keySet()) {
                if(result.get(key).size() >1 )
                    {
                        System.out.print("Повторяющаяся часть: "+word+" Количество повторений: "+ key + " Файлы: " + result.get(key) + "\n");
                    }
                }
        }

    }
}