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


   // public static void getRepeatInfo(String pathName){
        /*

        Method  search and count repeating part of file and print fileName, repeatPart, repeatCount

        Input data: path of file for text "C:\\Users\\user\\Desktop\\SomeDir\\1.txt"

                Arrays.stream(testArr)
                .collect((Collectors.groupingBy(p -> p, Collectors.counting())))
                .entrySet().stream().filter(t -> t.getValue() > 1)
                .forEach(key -> System.out.println(key.getKey() + " " + key.getValue()));
        */



        public static HashMap<String,HashMap<String,Integer>> repeatCountMap = new HashMap<>();

        public static void searchParts(String filePath){

            try(FileInputStream fis=new FileInputStream(filePath))
            {
                char space = ' ';
                int read = 0;
                StringBuilder buffworld = new StringBuilder();
                //HashMap<String,HashMap<String,Integer>> repeatCountMap = new HashMap<>();

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
            catch(IOException ex){
System.out.print("error");

            }


        }


    public static void main(String[] args) {

        List<String> filesPath =getFilePath("C:\\Users\\PC\\Desktop\\SomeDir");

for(String fPath: filesPath) {
    searchParts(fPath);
}
//searchParts("C:\\Users\\PC\\Desktop\\SomeDir\\2.txt");

        for(String repeatPart: repeatCountMap.keySet()){
            HashMap<String, Integer> hashMap = repeatCountMap.get(repeatPart);
            //for (String fileName: repeatCountMap.get(repeatPart).keySet()){
                //int hashcode = repeatCountMap.get(repeatPart).get(fileName).hashCode();
                //repeatCountMap.get(repeatPart).values()
            HashMap<Integer, ArrayList<String>> result = new LinkedHashMap<Integer, ArrayList<String>>();

            for (String key : hashMap.keySet()) {
                ArrayList<String> colName = null;
                if (!result.containsKey(hashMap.get(key))) {
                    colName = new ArrayList<String>();
                    colName.add(key);
                    result.put(hashMap.get(key), colName);
                } else {
                    colName = result.get(hashMap.get(key));
                    colName.add(key);
                    result.put(hashMap.get(key), colName);
                }


            }

            for (Integer key : result.keySet()) {
                if(key>2 && result.get(key).size() >1 )
                    {
                        System.out.print("Повторяющаяся часть: "+repeatPart+" Количество повторений: "+ key + " Файлы: " + result.get(key) + "\n");
                    }
                }





        }







    }
}