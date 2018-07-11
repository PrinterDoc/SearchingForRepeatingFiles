import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

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


    public static void compareDescFile(DescribePart descFile1, DescribePart descFile2){
        if (descFile1.repeatCount == descFile2.repeatCount && descFile1.repeatPart == descFile2.repeatPart && descFile1.fileName != descFile2.fileName){
            System.out.println("Файлы " + descFile1.fileName + " и " + descFile2.fileName +". " + " Повторяющаяся часть: " + descFile1.repeatPart + " Кол-во повторений: " + descFile1.repeatCount);
        }
    }


    public static void printEqualFile(ArrayList<DescribePart> filesArray){

        for(int i = 1; i<filesArray.size(); i++){
            for(int j =i+1 ;j<filesArray.size();j++){
                compareDescFile(filesArray.get(i), filesArray.get(j));
            }
        }
    }




   // public static void getRepeatInfo(String pathName){
        /*

        Method  search and count repeating part of file and print fileName, repeatPart, repeatCount

        Input data: path of file for text "C:\\Users\\user\\Desktop\\SomeDir\\1.txt"

                Arrays.stream(testArr)
                .collect((Collectors.groupingBy(p -> p, Collectors.counting())))
                .entrySet().stream().filter(t -> t.getValue() > 1)
                .forEach(key -> System.out.println(key.getKey() + " " + key.getValue()));
}
        */







    public static void main(String[] args) {

        List<String> filesPath =getFilePath("C:\\Users\\user\\Desktop\\SomeDir");
        //System.out.println(filesPath);


        //String[] testArr = {"lol","1","dSFd","df","dfg","fadf","gog","dGGD","lol","DGg","WGFdf","hfjk","gog","afbdv","gog","FDfef","gog","4r","dhjhgf","erg","ert","rt","uhgf","rgft"};
         /*       String sa ;
               Arrays.stream(testArr)
                .collect((Collectors.groupingBy(p -> p, Collectors.counting())))
                .entrySet().stream().filter(t -> t.getValue() > 1)
                .forEach(key -> System.out.println(key.getKey() + " " + key.getValue()));
*/

            DescribePart desc1 = new DescribePart("lol.txt",5,"dfdf.txt");
          DescribePart desc3 = new DescribePart("lol.txt",5,"dff.txt");
          DescribePart desc4 = new DescribePart("lol.txt",5,"dfdf.txt");
          DescribePart desc7 = new DescribePart("loltx",2,"df5.txt");
         DescribePart desc8 = new DescribePart("loltx",2,"df.txt");

        ArrayList<DescribePart> fileArray = new ArrayList<>();
        fileArray.add(desc8);
        fileArray.add(desc7);
        fileArray.add(desc3);
        fileArray.add(desc4);
        fileArray.add(desc1);
//
       // printEqualFile(fileArray);
        /*int[] array = {11, 14, 17, 11, 48, 33, 29, 11, 17, 22, 11, 48, 18};

        Map<Integer, Long> occurrences = Arrays.stream(array)
                .boxed()
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()));
                System.out.println(occurrences);
              */






        final int BUFFER_SIZE = 512;
        try {
            FileInputStream fis = new FileInputStream("C:\\Users\\user\\Desktop\\SomeDir\\1.txt");
            byte[] bufferPart = new byte[BUFFER_SIZE];
            Map<String,Long> buffMap = new HashMap<>();
            int read = 0;
            while ( (read = fis.read(bufferPart))> 0) {
              String buffStr = new String(bufferPart,"UTF-8");
              String[] buffArray = buffStr.split(" ");
                        Arrays.stream(buffArray)
                        .collect((Collectors.groupingBy(p -> p, Collectors.counting())))
                        .entrySet().stream().filter(t -> t.getValue() > 1)
                        .forEach(key -> buffMap.put(key.getKey(),key.getValue())/*key -> System.out.println(key.getKey() + " " + key.getValue())*/);


                Arrays.stream(buffArray)
                        .collect((Collectors.groupingBy(p -> p, Collectors.counting())))
                        .entrySet().stream().filter(t -> t.getValue() > 1)
                        .forEach(/*key -> buffMap.put(key.getKey(),key.getValue())*/key -> System.out.println(key.getKey() + " " + key.getValue()));
                }
            System.out.println(buffMap);
            fis.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}