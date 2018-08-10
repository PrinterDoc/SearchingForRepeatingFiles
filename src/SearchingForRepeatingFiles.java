import java.io.*;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.UTF_8;

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
                        //System.out.println(item.getName() + "  "+ item.getPath());
                        returnList.add(i,item.getPath());
                        i += 1;
                    } else System.out.println(item.getName() + " не является файлом");
                }
            } else System.out.println("Папка пуста");
        } else System.out.println("Папка не существует");
        return returnList;
    }


    public static HashMap<String,HashMap<String,Integer>> repeatCountMap = new HashMap<>();

    public static void searchParts(String filePath){

        try(FileInputStream fis=new FileInputStream(filePath))
            {
                char space = ' ';
                int read = 0;
                StringBuilder buffworld = new StringBuilder();
                while ( (read = fis.read())> 0){
                    if ((char)read != space){
                        buffworld.append((char)read);
                    }
                    if((char)read == space){
                        String word = buffworld.toString();
                        buffworld.delete(0,buffworld.length());
                        if(!repeatCountMap.containsKey(word)){
                        repeatCountMap.put(word, new HashMap<String,Integer>());
                        repeatCountMap.get(word).put(filePath,1);
                        }
                         else {
                            if (!repeatCountMap.get(word).containsKey(filePath)) {
                                repeatCountMap.get(word).put(filePath, 1);
                            } else {
                                repeatCountMap.get(word).put(filePath, repeatCountMap.get(word).get(filePath) + 1);
                            }
                        }
                    }
                }
            }
            catch(IOException ex){ System.out.print("error"); }
            }


    public static void main(String[] args) {

        List<String> filesPath =getFilePath("C:\\Users\\PC\\Desktop\\SomeDir");

        for(String fPath: filesPath) {
            searchParts(fPath);
        }


        for(String repeatPart: repeatCountMap.keySet()){
            HashMap<String, Integer> descPartMap = repeatCountMap.get(repeatPart);
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
                        System.out.print("Повторяющаяся часть: "+repeatPart+" Количество повторений: "+ key + " Файлы: " + result.get(key) + "\n");
                    }
                }
}

    }
}